
package org.datagen.tpch.schema;
import org.datagen.tpch.util.Tuple;

public class Supplier extends Base {

	public static Tuple getNext () {
		Integer suppkey = suppkey (); // key
		String name = "Supplier#" + String.format ("%09d",suppkey);
		String address = address (suppkey, (rnd(suppkey, 30)+10) );
		Integer nationkey = rnd (suppkey, 25);
		String phone = phone (suppkey);
		Double acctbal = acctbal (suppkey);
		String comment = D.text (63);  // FIXME: pass suppkey as seed

		return new Tuple (suppkey, name, address, 
							nationkey, phone, acctbal, comment);
	}

	public static final Integer suppkey () {
		return rnd (1000);
	}

	public final static Double acctbal (Integer suppkey) {
		// [ -999.99 .. 9,999.99 ]
		return pfmt2 (drnd (suppkey, 10999.99) - 999.99);
	}
};

