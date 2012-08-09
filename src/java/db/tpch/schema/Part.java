
package org.datagen.db.tpch.schema;
import org.datagen.db.core.*;
import org.datagen.db.tpch.catalog.Dictionary;

public class Part extends Base implements Relation {

	Dictionary D;

	public static Integer retailprice (Integer partkey) {
		return (90000 + ((partkey/10) % 20001) + 100 * (partkey/1000))/100;
	}

	long start = 0;

	public Part () {
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

		int mid = rnd (5);
		int bid = rnd (5);

		Integer partkey = partkey ();
		String name = D.name ();
		String mfgr = "Manufacturer#" + mid;
		String brand = "Brand#" + mid + bid;
		String type = D.type ();
		Integer size = rnd (50);
		String container = D.container ();
		Integer retailprice = retailprice (partkey);
		String comment = D.text (14);

		Tuple t = new Tuple (partkey, name, mfgr, brand, 
								size, container, retailprice, comment);
		return t;
	}

	public static Integer partkey () {
		return rnd (sf() * 200000);
	}
};

