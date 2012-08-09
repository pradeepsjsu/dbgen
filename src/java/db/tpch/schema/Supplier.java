
package org.datagen.db.tpch.schema;
import org.datagen.db.core.*;
import org.datagen.db.tpch.catalog.Dictionary;

public class Supplier extends Base implements Relation {

	long start = 0;
	Dictionary D;

	public Supplier () {
	}

	public void init (Properties map) {  
		start = 0;
		D = new Dictionary ();
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
		Integer suppkey = suppkey (); 
		String name = "Supplier#" + String.format ("%09d",suppkey);
		String address = Misc.address (suppkey, (rnd(suppkey, 30)+10) );
		Integer nationkey = rnd (suppkey, 25);
		String phone = Misc.phone (suppkey);
		Double acctbal = acctbal (suppkey);
		String comment = D.text (63);

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

