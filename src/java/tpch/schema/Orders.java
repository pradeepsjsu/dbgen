
package org.datagen.tpch.schema;
import org.datagen.tpch.util.Tuple;

public class Orders extends Base implements Relation {

	long start = 0;
	Lineitem li = null;

	public Orders () {
		init ();
	}

	public void init () {
		start = 0;
		li = new Lineitem ();
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

		Integer orderkey = orderkey (); 
		Integer custkey = rnd (sf() * 150000); 
		String orderstatus = choose ("F","O","P");
		Double totalprice = totalprice (orderkey);

		String orderdate = date (orderdate (orderkey));
		String orderpriority = D.priority ();
		String clerk = clerk ();

		Integer shippriority = 0;
		String comment = D.text (49);

		return new Tuple (orderkey, custkey, orderstatus, 
							totalprice, orderdate, orderpriority, 
							clerk, shippriority, comment);
	}

	public Double totalprice (Integer orderkey) {
		Double o_totprc = null;
		Integer l_extprc = li.extendeprice (orderkey);
		Double l_tax = li.tax (orderkey);
		Double l_disc = li.discount (orderkey);
		if (! isnull (l_extprc, l_tax, l_disc)) {
			o_totprc = (l_extprc * (1 + l_tax) * (1 - l_disc));
		}
		return o_totprc;
	}

	public String clerk () {
		String res = "Clerk#";
		int clerk_id = rnd (sf() * 1000);
		res += String.format ("%09d", clerk_id);
		return res;
	}

	public static Integer orderkey () {
		return rnd (sf() * 1500000 * 4);
	}

	public static final long orderdate (int seed) {
		int yy = 1991 + rnd (7);
		int mm = rnd (12);
		int dd = rnd (30);
		cal.set (yy, mm, dd);
		long millis = cal.getTimeInMillis ();
		return (millis - (151 * dayms));
	}
};
