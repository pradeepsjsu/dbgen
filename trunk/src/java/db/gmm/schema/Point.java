package org.datagen.db.gmm.schema;
import java.util.Random;

public class Point {
	long t; // timestamp
	double x, y, z;

	public Point (long tstamp, double x, double y, double z) {
		this.t = tstamp;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public long t () { return t; }
	public double x () { return x; }
	public double y () { return y; }
	public double z () { return z; }

	public String toString () {
		StringBuilder res = new StringBuilder (64);
		res.append (t)
		   .append (" |")
		   .append (String.format ("%9.3f",x))
		   .append ("|")
		   .append (String.format ("%9.3f",y))
		   .append ("|")
		   .append (String.format ("%9.3f",z))
		   .append ("|");
		//return t + "|" + x + "|" + y + "|" + z + "|";
		return res.toString ();
	}

	public static Point rnd (Random RNG, Range intvl) {
		long t = Util.time ();
		int lim = intvl.length ();
		double x = Util.rnd (RNG, lim) + intvl.min ();
		double y = Util.rnd (RNG, lim) + intvl.min ();
		double z = Util.rnd (RNG, lim) + intvl.min ();
		return new Point (t, x, y, z);
	}

	public static Point drnd (Random RNG, Range intvl) {
		long t = Util.time ();
		int lim = intvl.length ();
		double x = Util.drnd (RNG, lim) + intvl.min ();
		double y = Util.drnd (RNG, lim) + intvl.min ();
		double z = Util.drnd (RNG, lim) + intvl.min ();
		return new Point (t, x, y, z);
	}
};

class Util {

	public static int rnd (Random rng, int lim) {
		return (int) (rng.nextDouble () * lim);
	}

	public static double drnd (Random rng, double lim) {
		return (rng.nextDouble () * lim);
	}

	public static double norm (Random rng, double lim) {
		return rng.nextGaussian () * lim;
	}

	public static final long time () {
		return System.nanoTime ();
	}

	public static void prt () { System.out.println (); }
	public static void prt (Object obj) { System.out.println (obj); }
	public static void prtnl (Object obj) { System.out.print (obj); }
	public static void prt (String line) { System.out.println (line); }
};


