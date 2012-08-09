package org.datagen.db.gmm.schema;

import java.util.Random;
import org.datagen.db.core.*;

public class Model {

	ID id;
	Point mean, stdev;
	Random rng;

	public Model (ID id) {
		this.id = id;
		rng = new Random (Base.GOLDEN_RATIO_32 + id.hashCode ());
		mean = Point.rnd (rng, new Range (10, 1000));
		stdev = Point.rnd (rng, new Range (3, 30));
	}

	public void seek (Long start) {
		rng = new Random (Base.GOLDEN_RATIO_32 + id.hashCode () + start.hashCode ());
		mean = Point.rnd (rng, new Range (10, 1000));
		stdev = Point.rnd (rng, new Range (3, 30));
	}

	public ID id () {
		return id;
	}

	public Point mean () {
		return mean;
	}

	public Point stdev () {
		return stdev;
	}

	/*
	public void set (Point mean, Point stdev) {
		this.mean = mean;
		this.stdev = stdev;
	}
	*/

	public Point next () {
		long t = Util.time ();
		double x = mean.x() + Util.norm (rng, stdev.x());
		double y = mean.y() + Util.norm (rng, stdev.y());
		double z = mean.z() + Util.norm (rng, stdev.z());
		return new Point (t, x, y, z);
	}
};
