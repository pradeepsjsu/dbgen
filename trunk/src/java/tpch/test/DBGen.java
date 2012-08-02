
package org.datagen.tpch.test;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.datagen.tpch.schema.*;
import org.datagen.tpch.util.*;
import org.datagen.tpch.catalog.Dictionary;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableUtils;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

public class DBGen extends Configured implements Tool {

	static int M = 1000000;

    // custom input format that assigns ranges of longs
    static class RangeInputFormat extends InputFormat<LongWritable, NullWritable> {

        // custom input split consisting of a range on numbers...
        static class RangeInputSplit extends InputSplit implements Writable {

            long start_rec, num_recs; 

            public RangeInputSplit () {
            }

			public long start () {
				return start_rec;
			}

            public RangeInputSplit (long offset, long length) {
                start_rec = offset;
                num_recs = length;
            }

            public long getLength () throws IOException {
                return num_recs;
            }

            public String[] getLocations () throws IOException {
                return new String[] {};
            }

            @Override
            public void readFields (DataInput in) throws IOException {
                start_rec = WritableUtils.readVLong (in);
                num_recs = WritableUtils.readVLong (in);
            }

            @Override
            public void write (DataOutput out) throws IOException {
                WritableUtils.writeVLong (out, start_rec);
                WritableUtils.writeVLong (out, num_recs);
            }
        };

        // custom record reader that will produce a stream of
		// records for each input split as specified by the 
		// data-model associated with the split..
        static class RangeRecordReader extends RecordReader<LongWritable, NullWritable> {
            long start, end, cursor;
            LongWritable key;
			Text value;

            @Override
            public void initialize (InputSplit split, TaskAttemptContext context) { 
				try {
					RangeInputSplit my_split = (RangeInputSplit) split;
					start = my_split.start ();
					end = (start + my_split.getLength ());
					long n = (end - start) + 1;
					cursor = start;
					prtnl ("# split: [" + format (start) + "," + format (end) + "]");
					prt (" -- " +  format (n) + " recs");
					key = new LongWritable ();
				} catch (Exception e) {
					throw new RuntimeException (e);
				}
            }

            @Override
            public boolean nextKeyValue () throws IOException, InterruptedException {
                if (cursor < end) {
                    key.set (cursor);
					cursor = cursor + 1;
                    return true;
                } 
                return false;
            }

            @Override
            public LongWritable getCurrentKey () throws IOException,InterruptedException {
                return key;
            }

            @Override
            public NullWritable getCurrentValue () throws IOException,InterruptedException {
                return null;
            }

            public float getProgress () throws IOException {
                return cursor / (float) end;
            }

            public void close () throws IOException { 
				// do nothing
            }
        }; 

		@Override
        public List<InputSplit> getSplits (JobContext job) {
			final long recs_per_map = (long) (5*M); //from 1.25 -> part-li:200M
			// long min_recs = recs_per_map;
        	long tot_recs = job.getConfiguration().getLong ("dbgen.num.rows", 0);

			int num_splits = 1;
			long recs_per_split = tot_recs;
			long num_bytes = recs_per_split * 172l; // estimate for li

			if (tot_recs < recs_per_map) {
				prt ("# WARN: total_recs <  recs_per_map ");
				prt ("(" + tot_recs + " < " + recs_per_map + ")");
				// tot_recs = recs_per_map;
			}
			else {
            	num_splits = (int) (tot_recs / recs_per_map);
            	recs_per_split = tot_recs / num_splits;
				num_bytes = recs_per_split * 160l; // estimate for li
			}

			prt ();
            prt ("# InputSplits -> ");
			prt ("   -- num_splits: " + num_splits); 
			prt ("   -- recs/split: " + format (recs_per_split));
			prt ("   -- size/split: " + Base.hfmt2 (num_bytes));
			prt ("   -- total_recs: " + format (tot_recs));
			prt ();

            InputSplit[] split = new InputSplit[num_splits];
            long cursor = 0;
            for (int i = 0; i < num_splits - 1; i++) {
                split[i] = new RangeInputSplit (cursor, recs_per_split);
                cursor = cursor + recs_per_split;
            }
            split[num_splits - 1] = new RangeInputSplit (cursor, (tot_recs - cursor));

            List<InputSplit> split_list = new ArrayList<InputSplit> (split.length);
            for (InputSplit in_split : split) {
                split_list.add (in_split);
            }
            return split_list;
        }

        @Override
        public RecordReader<LongWritable, NullWritable>	createRecordReader 
										(InputSplit split, TaskAttemptContext context) {
			try {
         	   return new RangeRecordReader ();
			} catch (Exception e) {
				throw new RuntimeException (e);
			}
        }

    }; 

    public static class DBGenMapper extends Mapper<LongWritable, NullWritable, Text, Text> {

		long in_cnt, out_cnt;
		String relname = "orders";
		Text out_key, out_value;
		Dictionary dict;
		boolean firstTime;
		Tuple tuple;
		Lineitem li;
		Orders o;
		Customer c;
		Part p;
		Supplier s;
		Partsupp ps;
		Nation n;
		Region r;
		Relation rel = null;

		protected void setup (Context context) {
			prt (time() + " -- setup");
			prt ();
			in_cnt = 0;
			out_cnt = 0;
			//InputSplit split = context.getInputSplit ();
			dict = new Dictionary ();
			Configuration conf = context.getConfiguration ();
			String name = conf.get ("dbgen.table.name");
			prt ("# table.name: " + name);
			rel = Table.map (name);
			if (rel == null) {
				throw new RuntimeException ("ClassLoadError for " + name);
			}

			li = new Lineitem ();
			o = new Orders ();
			c = new Customer ();
			p = new Part ();
			s = new Supplier ();
			ps = new Partsupp ();
			n = new Nation ();
			r = new Region ();

			firstTime = true;
		}

		private void init (long offset) {
			if (rel instanceof Lineitem) {
				li.init ();
				li.reset ();
				li.seek (offset);
				li.close ();
			}
		}

        @Override
        protected void map (LongWritable in_key, NullWritable in_value, Context context)
  					      throws IOException, InterruptedException {
            long rec_id = in_key.get ();
			if (firstTime) {
				init (rec_id);
				firstTime = false;
			}
			out_key = new Text (Long.toString (rec_id));
			try {
				tuple = rel.getNext ();
			} catch (Exception e) {
				throw new RuntimeException ("getNext() raised exception");
			}
			out_value = new Text (tuple.toString ());
			in_cnt++;
            context.write (out_key, out_value);
			if (in_cnt % 100000 == 0) {
				prtnl (time() + " " + in_cnt + ": ");
				prt (tuple);
			}
			out_cnt++;
        }

		protected void cleanup (Context context) {
			prt ();
			prt (time() + " -- cleanup");
			prt ("  > in_recs: " + in_cnt);
			prt ("  > out_recs: " + out_cnt);
		}
    }; 

	public static final String time () {
		return Base.time ();
	}

	public static final String format (Object obj) {
		return Base.format (obj);
	}

	public static final void prt (Object obj) {
		Base.prt (obj);
	}

	public static final void prt (String line) {
		Base.prt (line);
	}

	public static final void prtnl (String line) {
		Base.prtnl (line);
	}

	public static void prt () {
		Base.prt ();
	}

	public int run (String[] args) throws IOException {
		try {
			if (args == null) {
				int sf = 1;
				for (Table tbl: Table.values ()) {
					String dest = "tpch/" + tbl.name ();
					long num_rows = tbl.size (sf);
					generate (tbl.name(), tbl.size(sf), dest);
				}
			}
			else {
				DBGen.generate (args[0], Long.parseLong (args[1]), args[2]);
			}
		} catch (Exception e) {
			throw new RuntimeException (e);
		}
		return 0;
	}

	// TODO: use Relation.size (scale_factor) to compute num_recs
    public static void generate (String tbl, long num_recs, String dest) throws Exception {

		assert (tbl != null);
		assert (num_recs > 0);
		assert (dest != null);

		long num_bytes = num_recs * 160l; // estimate for li
		String out_fd = dest + "/" + tbl;

		prt ();
		prt ("# GENERATE: ");
		prt ("   -- table: " + Base.quote (tbl));
		prt ("   -- records: " + Base.hfmt (num_recs));
		prt ("   -- out_size: " + Base.hfmt2 (num_bytes));
		prt ("   -- out_fd: " + Base.quote (out_fd));
		prt ();

		Configuration conf = new Configuration ();
		conf.setLong ("dbgen.num.rows", num_recs);
		conf.set ("dbgen.table.name", tbl);
		conf.setBoolean ("mapred.used.genericoptionparser",false);
		final String JAVA_OPT = "-Duser.timezone='America/Chicago' -Djava.io.tmpdir=/tmp -Djava.net.preferIPv4Stack=true -XX:CompileThreshold=10000 -XX:+DoEscapeAnalysis -XX:+UseNUMA -XX:-EliminateLocks -XX:+UseBiasedLocking -XX:+OptimizeStringConcat -XX:+UseFastAccessorMethods -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:+CMSIncrementalPacing -XX:CMSIncrementalDutyCycleMin=0 -XX:+UseCompressedOops -XX:+AggressiveOpts -XX:-UseStringCache";
		final String java_map_opt = "-Xmx256m " + JAVA_OPT;
		conf.set ("mapred.map.child.java.opts", java_map_opt);

		Job job = new Job (conf); 
		Path out_path = new Path (out_fd);
		FileOutputFormat.setOutputPath (job, out_path);
		FileSystem.get (conf).delete (out_path, true); 
		job.setJobName (tbl);
		job.setJarByClass (DBGen.class);
		job.setMapperClass (DBGenMapper.class);
		job.setNumReduceTasks (0);
		job.setOutputKeyClass (Text.class);
		job.setOutputValueClass (Text.class);
		job.setInputFormatClass (RangeInputFormat.class);
		job.setOutputFormatClass (SequenceFileOutputFormat.class);
		job.waitForCompletion (true);
	}

	public static void main (String[] args) throws Exception {
		int res = ToolRunner.run (new DBGen(), args); // calls DBGen.run ()
		System.exit (res);
	}
};
