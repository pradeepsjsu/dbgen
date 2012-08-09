package org.datagen.db.gmm.schema;
import org.datagen.db.core.*;

import java.util.Random;

public class Data extends Base implements Relation {

	Model[] model;
	Random rng;
	long cursor = 0;
	TupleStore store;
	Counter counter; // for recid
	long start;
	Die die;
	int[] hit;

	public Data () {
		model = null;
	}

	public void init (Properties properties) {
		// enum-map distType.get
		DistType dist_type = DistType.ZIPFIAN; // set this from conf
		Integer num_clusters = null;
		String dtype = null;
		if (properties != null) {
			dtype = properties.get ("datagen.db.dist_type");
			num_clusters = properties.getInt ("datagen.db.gmm.num_clusters");
		}
		if (num_clusters == null) {
			num_clusters = 10;
			//throw new RuntimeException ("num_clusters not set in conf");
		}
		model = new Model[num_clusters];
		hit = new int[num_clusters];
		for (int i = 0; i < num_clusters; i++) {
			model[i] = new Model (new ID (i+1));
			//prt (" model-" + i + " mean: " + model[i].mean ());
		}
		Double[] wt = dist_type.generate_weights (num_clusters);
		die = new Die (wt);
		store = new TupleStore ();
		seek (0);
	}

	private void fill_store () {
		long tstamp = Util.time ();
		long oid = counter.next ();
		//model = model[oid % model.length];
		int indx = pick_model (rng);
		Point pt = model[indx].next ();
		hit[indx] += 1;

		store.clear ();
		store.add (new Tuple (oid, 666, (indx+1000)));
		store.add (new Tuple (oid, 0, pt.x()));
		store.add (new Tuple (oid, 1, pt.y()));
		store.add (new Tuple (oid, 2, pt.z()));
	}

	public Tuple getNext () throws Exception {
		if (!store.hasMore()) {
			fill_store ();
		}
		Tuple rec = store.getNext ();
		assert (rec != null);
		return rec;
	}

	public void reset () {
		seek (start);
	}

	// selects a model by rolling 
	// a model.length-faced die 
	private int pick_model (Random rng) {
		return die.roll (rng);
	}

	public void seek (long offset) {
		rng = new Random (Base.GOLDEN_RATIO_32 + offset);
		for (int i = 0; i < model.length; i++) {
			model[i].seek (offset);
		}
		counter = new Counter (offset);
		start = offset;
	}

	public void close () {
		prt ();
		prt ("--- hits ---");
		for (int i = 0; i < model.length; i++) {
			prt (" face-" + i + ": " + hit[i]);
		}
		prt ();
	}
};

