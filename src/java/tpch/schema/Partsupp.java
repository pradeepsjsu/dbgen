
package org.datagen.tpch.schema;
import org.datagen.tpch.util.Tuple;

public class Partsupp extends Base {
	
	public static Tuple getNext () {
		Integer partkey = rnd (sf() * 2000000);
		Integer suppkey = rnd (sf() * 10000);
		Integer availqty = rnd (9999);
		Double supplycost = pfmt2 (drnd (1001.00) - 1.0);
		String comment = D.text (124);

		return new Tuple (partkey, suppkey, availqty, supplycost, comment);
	}
};

