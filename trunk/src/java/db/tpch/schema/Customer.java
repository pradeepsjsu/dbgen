
package org.datagen.db.tpch.schema;
import org.datagen.db.core.*;
import org.datagen.db.tpch.catalog.Dictionary;

public class Customer extends Base implements Relation {

	long start = 0;
	long limit = 0;
	final Dictionary D = new Dictionary ();

	public Customer () {
	}

	public void init (Properties map) {  
		start = 0;
		limit = Table.CUSTOMER.size (1.0);
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
		Integer custkey = custkey (start);
		String name = "Customer#";
		name += String.format ("%09d", custkey);
		String address = Misc.address ( (rnd(30)+10) );
		Integer nationkey = rnd(25);
		String phone = Misc.phone ();
		Double acctbal = pfmt2 (drnd (10999.99) - 999.99);
		String mktsegment = D.segment ();
		String comment = D.text (73);
		return new Tuple (custkey, name, address, nationkey, 
							phone, acctbal, mktsegment, comment);
	}

	public Integer custkey (long offset) {
		// FIXME: use offset and wrap around
		return rndL (limit); 
	}
};

