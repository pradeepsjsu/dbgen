
package org.datagen.tpch.schema;
import org.datagen.tpch.util.Tuple;

public class Part extends Base {

	public static Integer retailprice (Integer partkey) {
		return (90000 + ((partkey/10) % 20001) + 100 * (partkey/1000))/100;
	}

	public static Tuple getNext () {
		// -- schema
		Integer partkey;	
		String name;
		String mfgr;
		String brand;
		String type;
		Integer size;
		String container;
		Integer retailprice;
		String comment;

		partkey = partkey ();
		name = D.name ();
		int mid = rnd (5);
		mfgr = "Manufacturer#" + mid;
		int bid = rnd (5);
		brand = "Brand#" + mid + bid;
		type = D.type ();
		size = rnd (50);
		container = D.container ();
		retailprice = retailprice (partkey);
		comment = D.text (14);

		// construct output tuple
		Tuple t = new Tuple ();
		t.store (partkey);
		t.store (name);
		t.store (mfgr);
		t.store (brand);
		t.store (type);
		t.store (size);
		t.store (container);
		t.store (retailprice);
		t.store (comment);
		return t;
	}

	public static Integer partkey () {
		return rnd (sf() * 200000);
	}
};

