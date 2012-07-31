
package org.datagen.tpch.schema;
import org.datagen.tpch.util.Tuple;

public class Customer extends Base {
	public static Tuple getNext () {
		Integer custkey = custkey();
		String name = "Customer#";
		name += String.format ("%09d", custkey);
		String address = address ( (rnd(30)+10) );
		Integer nationkey = rnd(25);
		String phone = phone ();
		Double acctbal = pfmt2 (drnd (10999.99) - 999.99); // [ -999.99 .. 9,999.99 ]
		String mktsegment = D.segment ();
		String comment = D.text (73); // FIXME
		return new Tuple (custkey, name, address, nationkey, phone, acctbal, mktsegment, comment);
	}

	public static Integer custkey () {
		return rnd (sf() * 150000);
	}
};

