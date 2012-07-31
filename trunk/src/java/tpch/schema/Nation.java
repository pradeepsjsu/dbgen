
package org.datagen.tpch.schema;
import org.datagen.tpch.util.Tuple;
import org.datagen.tpch.catalog.Dictionary;

import java.util.List;
import java.util.ArrayList;

public class Nation extends Base {
	public enum NTuple {
		ALGERIA (0, 0),
		ARGENTINA (1, 1),
		BRAZIL (1, 1),
		CANADA (3, 1),
		EGYPT (4, 4),
		ETHIOPIA (5, 0),
		FRANCE (6, 3),
		GERMANY (7, 3),
		INDIA (8, 2),
		INDONESIA (9, 2),
		IRAN (10, 4),
		IRAQ (11, 4),
		JAPAN (12, 2),
		JORDAN (13, 4),
		KENYA (14, 0),
		MOROCCO (15, 0),
		MOZAMBIQUE (16, 0),
		PERU (17, 1),
		CHINA (18, 2),
		ROMANIA (19, 3),
		SAUDI_ARABIA (20, 4),
		VIETNAM (21, 2),
		RUSSIA (22, 3),
		UNITED_KINDOM (23, 3),
		UNITED_STATE (24, 1);

		private final Integer nationkey;
		private final Integer regionkey;
		private static final List<NTuple> nlist = new ArrayList<NTuple> ();

		static {
			for (NTuple n : NTuple.values()) {
				nlist.add (n);
			}
		}

		private NTuple (Integer nationkey, Integer regionkey) {
			this.nationkey = nationkey;
			this.regionkey = regionkey;
		}

		public Integer nationkey () {
			return nationkey;
		}

		public Integer regionkey () {
			return regionkey;
		}

		public static NTuple get (int i) {
			return nlist.get (i);
		}
	};

	public static Tuple getNext () {
		int i = Base.rnd (25) - 1;
		NTuple n = NTuple.get (i);
		return new Tuple (n.nationkey (), n.name (), n.regionkey (), Dictionary.text (95));
	}
};

