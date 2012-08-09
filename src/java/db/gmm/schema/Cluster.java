package org.datagen.db.gmm.schema;
import org.datagen.db.core.*;

import java.util.Random;

public class Cluster {

	ID id;
	public static final int GOLDEN_RATIO_32 = 0x09e3779b9;
	Random rng;
	Model model;

	public Cluster (ID id) {
		this.id = id;
		rng = new Random (GOLDEN_RATIO_32 + id.hashCode ());
		model = null;
	}

	public Cluster (ID id, Model model) {
		this.id = id;
		this.model = model;
		rng = new Random (GOLDEN_RATIO_32 + id.hashCode ());
	}

	public ID id () {
		return id;
	}

	public Model model () {
		return model;
	}

	public void set (Model model) {
		this.model = model;
	}

	public Random rng () {
		return rng;
	}

	public Point getNext () {
		return random (rng, model);
	}

	public static Point random (Random rng, Model model) {
		long tstamp = Util.time ();
		double x = model.mean.x() + Util.norm (rng, model.stdev.x());
		double y = model.mean.y() + Util.norm (rng, model.stdev.y());
		double z = model.mean.z() + Util.norm (rng, model.stdev.z());
		return (new Point (tstamp, x, y, z));
	}
};

/*
public class Clusters {

	final int limit = 10; // max clusters
	final Model model[];

	public void init (long offset) {
		rng = new Random (GOLDEN_RATIO_32);
		model = new Model[limit];
		Range itvl_mean = new Range (10, 1000);
		Range itvl_stdev = new Range (3, 30);
		for (int i = 0; i < limit; i++) {
			Point mean = Point.rnd (rng, itvl_mean);
			Point stdev = Point.rnd (rng, itvl_stdev);
			model[i] = new Model (mean, stdev);
		}
		cursor = 0;
	}

	public Tuple getNext () {
		int indx = cursor % limit;
		return new Tuple (indx, model.mean.x(), model.mean.y(), model.mean.z());
	}
};
*/
