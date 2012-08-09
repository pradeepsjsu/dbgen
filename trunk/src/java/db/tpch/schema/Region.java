
package org.datagen.db.tpch.schema;
import org.datagen.db.core.*;
import org.datagen.db.tpch.catalog.Dictionary;

import java.util.List;
import java.util.ArrayList;

public class Region extends Base implements Relation {

	public enum RTuple {
		ASIA (1),
		AMERICA (2),
		AFRICA (3),
		EUROPE (4);

		private final Integer regionkey;
		private static final List<RTuple> rlist = new ArrayList<RTuple> ();

		static {
			for (RTuple r : RTuple.values ()) {
				rlist.add (r);
			}
		}

		private RTuple (Integer regionkey) {
			this.regionkey = regionkey;
		}

		public Integer regionkey () {
			return regionkey;
		}

		public static RTuple get (int i) {
			return rlist.get (i);
		}
	};

	int limit = 0;
	long start = 0;

	public Region () {
	}

	public void init (Properties map) {
		start = 0;
		limit = (int) Table.REGION.size (1.0);
		limit = 4;
		assert (limit > 0);
	}

	public void reset () {
		start = 0;
	}

	public void seek (long offset) {
		start = offset;
	}

	public void close () {
	}

	public Tuple getNext () throws Exception {
		//prt (" start: " + start + " limit: " + limit);
		int i = (int) (start % limit);
		RTuple r = RTuple.get (i);
		start = start + 1;
		return new Tuple (r.regionkey (), r.name (), Dictionary.text (95));
	}
};
