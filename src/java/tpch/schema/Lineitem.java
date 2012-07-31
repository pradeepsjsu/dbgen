
package org.datagen.tpch.schema;
import org.datagen.tpch.util.Tuple;

public class Lineitem extends Base {
	static long cursor  = 0;

	public static Tuple getNext () {
		Integer orderkey = Orders.orderkey ();
		Long linenumber = next (); // key
		Integer partkey = Part.partkey ();
		Integer suppkey = suppkey (partkey);
		Integer quantity = rnd (50);
		Integer extendedprice = quantity * Part.retailprice (partkey);
		Double discount = pfmt2 (drnd (0.2) - 1.0);
		Double tax = pfmt2 (drnd (0.08) - 1.0);
		//--
		String returnflag = choose ("R", "A", "N");
		String linestatus = choose ("O", "F");
		// FIXME
		long o_date = Orders.orderdate (orderkey);
		long s_date = shipdate (o_date, orderkey);
		String shipdate = date (s_date);
		String commitdate = date (commitdate (o_date, orderkey));
		String receiptdate = date (receiptdate (s_date, orderkey));
		String shipinstruct = D.instruction ();
		String shipmode = D.mode ();
		String comment = D.text (27);
		return new Tuple (orderkey, linenumber, partkey, suppkey, quantity, extendedprice, discount, tax, returnflag, linestatus, shipdate, commitdate, receiptdate, shipinstruct, shipmode, comment);
	}

	public static final void start (long start) {
		cursor = start - 1;
	}

	public static final long cursor () {
		return cursor;
	}

	private static long next () {
		cursor = cursor + 1;
		return cursor;
	}

	private static int suppkey (int partkey) {
		int S = sf() * 10000;
		int i = rnd (4) - 1;
		int suppkey = (partkey + (i * ((S/4) + (int)(partkey-1)/S))) % S + 1;
		return suppkey;
	}

	// shipdate: orderdate + rnd (121);
	public static final long shipdate (long o_date, int seed) {
		long step = rnd (121) * dayms;
		return (o_date + step);
	}

	// commitdate: orderdate + rnd (30 .. 90);
	public static final long commitdate (long o_date, int seed) {
		long step = (30 + rnd (60)) * dayms;
		return (o_date + step);
	}

	// receiptdate: shipdate + rnd (1 .. 30);
	public static final long receiptdate (long s_date, int seed) {
		long step = (rnd (30)) * dayms;
		return (s_date + step);
	}
};

