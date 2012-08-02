
package org.datagen.tpch.schema;
import org.datagen.tpch.util.Tuple;

public class Lineitem extends Base implements Relation {

	long start = 0;
	Orders o = null;

	public void init () {
		start = 0;
		o = new Orders ();
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
		Long linenumber = id (); 
		Integer orderkey = o.orderkey (); // XXX: pass linenumber?
		Integer partkey = Part.partkey ();
		Integer suppkey = suppkey (partkey);
		Integer quantity = quantity (partkey);
		Integer extendedprice = quantity * Part.retailprice (partkey);
		Double discount = pfmt2 (discount (partkey));
		Double tax = pfmt2 (tax (partkey));
		String returnflag = choose ("R", "A", "N");
		String linestatus = choose ("O", "F");
		long o_date = Orders.orderdate (orderkey);
		long s_date = shipdate (o_date, orderkey);
		String shipdate = date (s_date);
		String commitdate = date (commitdate (o_date, orderkey));
		String receiptdate = date (receiptdate (s_date, orderkey));
		String shipinstruct = D.instruction ();
		String shipmode = D.mode ();
		String comment = D.text (27);

		return new Tuple (orderkey, linenumber, partkey, suppkey, 
							quantity, extendedprice, discount, tax, 
							returnflag, linestatus, shipdate, commitdate, 
							receiptdate, shipinstruct, shipmode, comment);
	}
	
	// FIXME XXX TODO
	// -- to ensure data reproducibility, the methods
	// -- should use 'partkey' as hash or seed value

	private final Integer quantity (final Integer partkey) {
		return rnd (50); 
	}

	public final Integer extendeprice (final Integer partkey) {
		return quantity (partkey) * Part.retailprice (partkey);
	}

	public final Double discount (final Integer partkey) {
		return (drnd (0.2) - 1.0);
	}

	public final Double tax (final Integer partkey) {
		return (drnd (0.08) - 1.0);
	}

	private final long id () {
		return start++;
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

