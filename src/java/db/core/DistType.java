package org.datagen.db.core;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;

public enum DistType {
	ZIPFIAN ("zipfian") {
		// weights with an exponential dropoff
		public Double[] generate_weights (int n) {
			double zip_const = 0.97; // set this from conf
			Double[] wt = new Double[n];
			for (int i = 0; i < n; i++) {
				wt[i] = 1.0 / Math.pow (i+1, zip_const);
			}
			return normalize (wt);
		}
	},
	RANDOM ("random") {
		public Double[] generate_weights (int n) {
			Double[] wt = new Double[n];
			for (int i = 0; i < wt.length; i++) {
				wt[i] = rng.nextDouble ();
			}
			return normalize (wt);
		}
	},
	CONST ("const") {
		public Double[] generate_weights (int n) {
			Double[] wt = new Double[n];
			for (int i = 0; i < wt.length; i++) {
				wt[i] = 1.0/n;
			}
			return wt;
		}
	};

	private static final Map<String, DistType> NAME_TO_DIST = 
		new HashMap<String, DistType> ();
	private static Random rng = 
		new Random (Base.GOLDEN_RATIO_32);

	static {
		for (DistType dist_type: values ()) {
			NAME_TO_DIST.put (dist_type.id, dist_type);
		}
	}

	public abstract Double[] generate_weights (int n);
	private final String id;

	DistType (String id) {
		this.id = id;
	}

	public String id () {
		return id;
	}

	public String toString () {
		return name ();
	}

	public static DistType get (String name) {
		return NAME_TO_DIST.get (name);
	}

	public static Double[] normalize (Double[] elem) {
		double sum = sum (elem);
		double delta = Math.abs (1.0 - sum);
		//elem[0] = sum < 1.0 ? elem[0] += delta : elem[0] -= delta;
		Double[] res = new Double[elem.length];
		for (int i = 0; i < elem.length; i++) {
			res[i] = elem[i]/sum;
		}
		return res;
	}

	public static Double sum (Double[] elem) {
		Double sum = 0.0;
		for (int i = 0; i < elem.length; i++) {
			sum += elem[i];
		}
		return sum;
	}

	public static void check_weights (Double[] wt, int totpts) {
		double chk = 0;
		long mytot = 0;
		for (int i = 0; i < wt.length; i++) {
			long numpts = Math.round (wt[i]*totpts);
			mytot += numpts;
			Util.prt (i + ": " + Util.fmt(wt[i]) + "    " + numpts);
			chk += wt[i];
		}
		Util.prt ("\n sum: " + chk + " totpts: " + totpts + " mytot: " + mytot);
	}
};
