
package org.datagen.db.tpch.schema;
import org.datagen.db.core.*;
import org.datagen.db.tpch.catalog.Dictionary;

public class Partsupp extends Base implements Relation {
	
	long start = 0;
	final Dictionary D = new Dictionary ();

	public Partsupp () {
	}

	public void init (Properties map) {
		start = 0;
	}

	public void reset () {
		start = 0;
	}

	public void seek (long offset) {
		start = offset;
	}

	public void close () {
	}

	public Tuple getNext () throws Exception {
		Integer partkey = rnd (sf() * 2000000);
		Integer suppkey = rnd (sf() * 10000);
		Integer availqty = rnd (9999);
		Double supplycost = pfmt2 (drnd (1001.00) - 1.0);
		String comment = D.text (124);

		return new Tuple (partkey, suppkey, availqty, supplycost, comment);
	}
};

