
package org.datagen.tpch.schema;
import org.datagen.tpch.util.Tuple;

public class Orders extends Base {

	public static Tuple getNext () {
		Integer 	orderkey;
		Integer 	custkey;
		String 		orderstatus; // char(1)
		Double 		totalprice;
		String 		orderdate;
		String 		orderpriority; // char(15)
		String 		clerk; // char(15)
		Integer 	shippriority; 
		String 		comment; // char(79);

		orderkey = orderkey (); //[1..(SF * 150,000 * 4)]
		custkey = rnd (sf() * 150000); // [1..(SF * 150,000)]
		// orderstatus -> l_linestatus {'F','O','P'}
		orderstatus = choose ("F","O","P");
		// sum (l_extprc * (1 + l_tax) * (1 - l_discnt)
		totalprice = nextDouble (1793); 

		// UNIFORM (start_date, (end_date - 151))
		orderdate = date (orderdate (orderkey));
		orderpriority = D.priority ();

		// "Clerk000,000,001"; // [000,000,001 .. (SF * 1000)]
		clerk = "Clerk#";
		int clerk_id = rnd (sf() * 1000);
		clerk += String.format ("%09d", clerk_id);

		shippriority = 0;
		comment = D.text (49);

		Tuple inst = new Tuple ();
		inst.store (orderkey);
		inst.store (custkey);
		inst.store (orderstatus);
		inst.store (totalprice);
		inst.store (orderdate);
		inst.store (orderpriority);
		inst.store (clerk);
		inst.store (shippriority);
		inst.store (comment);
		return inst;
	}

	public static Integer orderkey () {
		return rnd (sf() * 1500000 * 4);
	}

	public static final long orderdate (int seed) {
		// uniform (STARTDATE, (ENDDATE - 151))
		int yy = 1991 + rnd (7);
		int mm = rnd (12);
		int dd = rnd (30);
		cal.set (yy, mm, dd);
		long millis = cal.getTimeInMillis ();
		return (millis - (151 * dayms));
	}
};

