
package org.datagen.tpch.util;
import org.datagen.tpch.schema.*;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

/** Enumeration of all TPCH relations */
public enum Relation {
	LINEITEM (Lineitem.class, "lineitem", 6000000, false),
	ORDERS (Orders.class, "orders", 1500000, false),
	CUSTOMER (Customer.class, "customer", 150000, false),
	PART (Part.class, "part", 200000, false),
	PARTSUPP (Partsupp.class, "partsupp", 800000, false),
	SUPPLIER (Supplier.class, "supplier", 10000, false),
	NATION (Nation.class, "nation", 25, true),
	REGION (Region.class, "region", 5, true);

	private static final HashMap<String, Relation>  STRING_TO_TYPE = new HashMap<String, Relation> ();
	private static final HashMap<Class<? extends Base>, Relation>  CLASS_TO_TYPE = new HashMap<Class<? extends Base>, Relation> ();
	private static final List<Relation> rlist = new ArrayList<Relation> ();
	

	static {
		for (Relation rel : values ()) {
			STRING_TO_TYPE.put (rel.name, rel);
			CLASS_TO_TYPE.put (rel.child_class, rel); 
		}
	}

	private final Class<? extends Base> child_class;
	private final String name;
	private final int size;
	private final boolean isConst;

	Relation (Class<? extends Base> child_class, String name, int size, boolean isConst) {
		this.child_class = child_class;
		this.name = name;
		this.size = size;
		this.isConst = isConst;
	}

	public String toString () {
		return name;
	}

	public String getName () {
		return name;
	}

	public long numRows (double sf) {
		return size (sf);
	}

	public long size (double sf) {
		if (isConst) { 
			return size;
		}
		return (long) (size * sf);
	}

	public Class<? extends Base> getInstance () {
		return child_class;
	}

	public Base newInstance () {
		try {
			return child_class.newInstance ();
		} 
		catch (InstantiationException e) {
			throw new RuntimeException (child_class.getName (), e);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException (child_class.getName (), e);
		}
	}
};
