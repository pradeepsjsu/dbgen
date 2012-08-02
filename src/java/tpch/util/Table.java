
package org.datagen.tpch.util;
import org.datagen.tpch.schema.*;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

/** Enumeration of all TPCH relations */
public enum Table {

	LINEITEM (Lineitem.class, "lineitem", 6000000, false),
	ORDERS (Orders.class, "orders", 1500000, false),
	CUSTOMER (Customer.class, "customer", 150000, false),
	PART (Part.class, "part", 200000, false),
	PARTSUPP (Partsupp.class, "partsupp", 800000, false),
	SUPPLIER (Supplier.class, "supplier", 10000, false),
	NATION (Nation.class, "nation", 25, true),
	REGION (Region.class, "region", 5, true);

	private static final HashMap<String, Table>  NAME_TO_TBL = new HashMap<String, Table> ();

	static {
		for (Table tbl: values ()) {
			NAME_TO_TBL.put (tbl.id, tbl);
		}
	}

	private final Class<?> clazz;
	private final String id; // avoiding 'name' .. Enum.name()
	private final int size;
	private final boolean isConst;

	Table (Class<?> clazz, String id, int size, boolean isConst) {
		this.clazz = clazz;
		this.id = id;
		this.size = size;
		this.isConst = isConst;
	}

	// -- getters 
	public String id () {
		return id;
	}

	public Class<?> getInstance () {
		return clazz;
	}

	public long size (double sf) {
		if (isConst) { 
			return size;
		}
		return (long) (size * sf);
	}

	public boolean isConst () {
		return isConst;
	}

	public static Table get (String name) {
		return NAME_TO_TBL.get (name);
	}

	//  -- utility methods

	public Relation newInstance () {
		Relation rel = null;
		try {
			rel = (Relation) clazz.newInstance ();
		} 
		catch (InstantiationException e1) { 
			//throw new Exception (clazz.getName(), e1);
		}
		catch (IllegalAccessException e2) { 
			//throw new Exception (clazz.getName(), e2);
		}
		return rel;
	}

	public static Relation map (String name) {
		Relation rel = null;
		Table tbl = NAME_TO_TBL.get (name);
		if (tbl != null) {
			rel = tbl.newInstance ();
		}
		return rel;
	}

	public static final Class<?> resolveClass (String name) throws Exception {
		Class<?> c = null;
		try {
			c = Class.forName (name);
		} catch (ClassNotFoundException e) {
			throw new Exception (e);
		}
		return c;
	}

	public String toString () {
		return name ();
	}
};
