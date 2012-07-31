
package org.datagen.tpch.schema;
import org.datagen.tpch.util.Tuple;
import org.datagen.tpch.catalog.Dictionary;

import java.util.List;
import java.util.ArrayList;

public class Region extends Base {

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

	public static Tuple getNext () {
		int i = Base.rnd (4) - 1;
		RTuple r = RTuple.get (i);
		return new Tuple (r.regionkey (), r.name (), Dictionary.text (95));
	}
};
