
package org.datagen.tpch.schema;

import org.datagen.tpch.util.Tuple;
import org.datagen.tpch.catalog.Dictionary;

import java.util.Random;
import java.util.HashMap;
import java.util.Calendar;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

enum Element {
	AUTOMOBILE ("Segments"),
	BUILDING ("Segments"),
	FURNITURE ("Segments"),
	MACHINERY ("Segments"),
	HOUSEHOLD ("Segments"),
	HIGH ("Priorities");
	String type;

	Element (String type) {
		this.type = type;
	}

	String type () {
		return type;
	}
};

public class Base {

	protected static final Dictionary D = new Dictionary ();
    public static final long GOLDEN_RATIO_64 = 0x9e3779b97f4a7c13L;
    public static final int GOLDEN_RATIO_32 = 0x09e3779b9;
	private static int scale_factor = 1;
    private static final Random rng = new Random (GOLDEN_RATIO_64);
    private static final MathContext mcx = new MathContext (7);
    private static final DecimalFormat df = new DecimalFormat ();
	
	static final long dayms = 86400 * 1000;
	static final Calendar cal = Calendar.getInstance ();
	static final String lower = "abcdefghijklmnopqrstuvwxyz";
	static final String upper = lower.toUpperCase ();
	static final String alphabets = lower + upper;
	static final String alphabet = alphabets;
	static final String numerals = "0123456789";
	static final String misc = "@$^~`;.?&_"; 
	static final String math = "<>=!~";
	static final String arith = "+/-*%";
	static final String spl = "([{,|:#}])";
	// excluding math, arith, spl
	static final String cv = alphabets + numerals;
	static final String ck = alphabets + numerals + misc;
	static final String word[] = {
					"furiously", "haggle", "lavender", "slurpy", 
					"sleepy", "unusual", "carefully", "pinto", 
					"beans", "instructions", "requests", "alongside", 
					"of", "deposits", "dependencies", "express", 
					"foxes", "wake", "pending", "fluffily", "final", 
					"slyly", "blithely", "cajole", "silent", "the", 
					"to", "against", "according", "ironic", "gifts", 
					"even", "packages", "thinly", "special","nag", 
					"bold", "integrate", "at", "above", "waters", 
					"dolphins", "must", "theodolites", "sheaves", 
					"bus", "asymptotes", "condition", "generalization", 
					"parse", "string", "entry", "user", "deployed", 
					"syslog", "handler", "automated", "fields", "error", 
					"too", "many", "malformed", "asynchronous", "raise", 
					"follows", "can't", "don't", "stop", "difficult", 
					"program", "such", "thread", "continuable", "possible", 
					"jump", "violet", "red", "blue", "orange", "green", 
					"restart", "policy", "appropriate", "chunk", "key", 
					"foo", "bar", "door", "floor", "in", "not", "yes", 
					"no", "I", "can", "do", "don't", "a", "airplane", 
					"train", "ship", "bike", "car"
					};

	public static final Integer K = 1000;
	public static final Integer M = 1000 * 1000;
	public static final Integer B = 1000 * 1000 * 1000; 
	public static final Long T = 1000l * 1000l * 1000l * 1000l;

	// Integer.MAX_VALUE is 2^32-1 := 2,147,483,647 (2.1 billion)
	// Long.MAX_VALUE is 2^63-1 := 9,223-372,036-854,775-807 (9.2 trillion?)

	static int keylen = 17;
	static int wordlen = 3;

	public static final String nextKey () {
		int len;
		len = keylen + rng.nextInt (3);
		int max = ck.length ();
		char res[] = new char[len+3];
		res[0] = ' ';
		res[1] = ' ';
		res[2] = ' ';
		for (int i = 3; i < len; i++) {
			int indx = rng.nextInt (max);
			res[i] = ck.charAt(indx);
		}
		return new String (res);
	}

	public static final String word () {
		int indx = rnd (word.length) - 1;
		return word[indx] + " ";
	}

	private static final String digit () {
		int pos = rnd (numerals.length() - 1);
		return numerals.substring (pos,pos+1);
	}

	private static final String ndigit () {
		String ch = digit ();
		while (ch.equals ("0")) {
			ch = digit ();
		}
		return ch;
	}

	private static final char alphabet () {
		return alphabet.charAt ( rnd (alphabet.length()-1) );
	}

	private static final String digit (int len) {
		String res = "";
		for (int i = 0; i < len; i++) {
			res += digit ();
		}
		return res;
	}

	private static final String ndigit (int len) {
		String res = "";
		for (int i = 0; i < len; i++) {
			res += ndigit ();
		}
		return res;
	}

	public static final String phone () {
		String res = "";
		res += ndigit (2);
		res += "-";
		res += ndigit (3);
		res += "-";
		res += ndigit (3);
		res += "-";
		res += digit (4);
		return res;
	}

	public static final String phone (Integer key) {
		return phone ();
	}

	public static final String address (int maxlen) {
		String res = "";
		for (int i = 0; i < maxlen; i++) {
			res += alphabet ();
		}
		return res;
	}

	public static final String address (Integer seed, int maxlen) {
		return address (maxlen);
	}


	public static final String myphrase (int maxlen) {
		StringBuilder res = new StringBuilder (maxlen);
		while (res.length () < maxlen) {
			res.append (word());
		}
		return res.toString ();
	}

	public static final String nextWord () {
		int len = wordlen + rng.nextInt (7);
		String res = "";
		for (int i = 1; i < len; i++) {
			res += word();
		}
		return res;
	}

	// rand int from [1, lim]
	public static final int rnd (int lim) {
		return nextInt (lim) + 1;
	}

	public static final int rnd (Integer seed, int lim) {
		return nextInt (lim) + 1;
	}

	// rand dbl from [1, lim]
	public static final double drnd (double lim) {
		return nextDouble (lim) + 1.0;
	}

	public static final double drnd (Integer seed, double lim) {
		return nextDouble (lim) + 1.0;
	}


	public static final String choose (final Object... arg) {
		Object res = null;
		if (arg != null) {
			if (arg.length == 1) {
				res = arg[0];
			}
			else {
				res = arg[rnd (arg.length) - 1];
			}
		}
		return res.toString ();
	}

	public static final String choose (final String seed, final Object... arg) {
		return choose (arg);
	}

	public static final int sf () {
		return scale_factor;
	}

	// start_date: 1992-01-01
	// current_date: 1995-06-17
	// end_date: 1998-12-31
	// UNIFORM (start_date, (end_date - 151))
	public static final String nextDate () {
		int YY = 1991 + rnd (7); // FIXME
		int mm = rnd (12);
		int dd = rnd (30);
		String res = "";
		res += String.format ("%4s", YY);
		res += "-";
		res += String.format ("%02d", mm);
		res += "-";
		res += String.format ("%02d", dd);
		return res;
	}

	// rand int from [0, lim-1]
	public static final Integer nextInt (int lim) {
		return (int) (rng.nextDouble () * lim);
	}

	public static final Double nextDouble (double lim) {
		return (rng.nextDouble() * lim);
	}

	private static final Double doubleValue (Double val) {
		BigDecimal bigd;
		try {
			bigd = new BigDecimal (val, mcx);
		} catch (NumberFormatException nfe) {
			return 0.001; // FIXME
		}
		return bigd.doubleValue ();
	}

	// --- various formatting utils ----

	public static final Double pfmt (final Double value) {
		//String res = df.format (value);
		//return  Double.parseDouble (String.format ("%.2f", value));
		int frac = (int) (value % 100);
		if (frac < 10) {
			frac = frac * 10;
		}
		if (frac < 0) {
			frac = -frac;
		}
		Long dec = value.longValue ();
		String res = dec + "." + frac;
		prt (" --- value: " + value + " dec: " + dec + " frac: " + frac);
		return Double.parseDouble (res);
	}

	public static final Double pfmt2 (final Double value) {
		//String res = df.format (value);
		return  Double.parseDouble (String.format ("%.2f", value));
		/*
		int frac = (int) (value % 1000);
		long dec = value.longValue ();
		String res = dec + "." + frac;
		return res;
		*/
	}

	public static final String hfmt (final Long value) {
		if (value == null) return "null";
		if (value < 1048576l) {
			return format (value);
		}
		Double mb = value / M * 1.0;
		if (mb < 1000) {
			return format (mb) + " million";
		}
		Double gb = mb / 1000 * 1.0;
		return format (gb) + " billion";
	}

	public static final String hfmt2 (final Long value) {
		if (value == null) return "null";
		Double kb = value / 1024.0;
		if (value < 1048576l) {
			return format (kb) + " KB";
		}
		Double mb = kb / 1024.0;
		if (mb < 1024) {
			return format (mb) + " MB";
		}
		Double gb = mb / 1024.0;
		if (gb < 1024) {
			return format (gb) + " GB";
		}
		Double tb = gb / 1024.0;
		return format (tb) + " TB";
	}

	public static final String quote (String value) {
		if (value == null) return "''";
		return "'" + value.trim() + "'";
	}

	public static final String wrap (String value) {
		if (value == null) return "()";

		return "(" + value.trim() + ")";
	}

	public static final String unwrap (String value) {
		if (value == null) return "()";
		value = value.trim ();
		if (value.startsWith ("(") && value.endsWith (")")) {
			value = value.substring (1, value.length()-1);
		}
		return value.trim ();
	}

	public static final String hfmt (final Integer value) {
		if (value == null) return "null";
		return hfmt (value.longValue ());
	}

	public static final String format (final Object obj) {
		if (obj instanceof Integer) {
			Integer ival = (Integer) obj;
			return format (ival);
		}
		else if (obj instanceof Double) {
			Double dval = (Double) obj;
			return format (dval);
		}
		else if (obj instanceof Long) {
			Long lval = (Long) obj;
			return format (lval);
		}
		else if (obj instanceof String) {
			String str = (String) obj;
			return format (str);
		}
		else if (obj == null) {
			return "";
		}
		return format (obj.toString ());
	}

	public static final String format (final Integer value) {
		if (value == null) return "null";
		DecimalFormat df = new DecimalFormat ("###,###");
		return df.format (value);
	}

	public static final String format (final Long value) {
		if (value == null) return "null";
		DecimalFormat df = new DecimalFormat ("###,###");
		return df.format (value);
	}

	public static final String format (final Double value) {
		if (value == null) return "null";
		DecimalFormat df = new DecimalFormat ("###,###.###");
		return df.format (value);
	}

	public static final String date (long millis) {
		cal.setTimeInMillis (millis);
		SimpleDateFormat fmt = new SimpleDateFormat ("yyyy-MM-dd");
		return fmt.format (cal.getTime());
		/*
		StringBuilder res = new StringBuilder (12);
		res.append (String.format ("%4s", cal.YEAR))
		   .append ("-")
		   .append (String.format ("%02d", cal.MONTH)) 
		   .append ("-")
		   .append (String.format ("%02d", cal.DATE));
		return res.toString ();
		*/
	}

	public static final String time () {
		SimpleDateFormat fmt = new SimpleDateFormat ("HH:mm:ss");
		return "[" + fmt.format (Calendar.getInstance().getTime()) + "] ";
	}

	// print utils..

	public static final void prt (final Object obj) {
		System.out.println (obj);
	}

	public static final void prt (final String line) {
		System.out.println (line);
	}

	public static final void prtnl (final String line) {
		System.out.print (line);
	}

	public static final void prt () {
		System.out.println ();
	}

	public static final void prt (byte[] b) {
		for (int i = 0; i < b.length; i++) {
			System.out.print (b[i] + " ");
		}
		System.out.println ();
	}

	public static final void header (String line) {
		prt (hr());
		prt (line);
		prt (hr());
	}

	public static final void footer () {
		prt (hr());
	}

	public static final String hr () {
		return hr (72);
	}

	public static final String hr (int len) {
		String res = "";
		for (int i = 0; i < len; i++) {
			res += "-";
		}
		return res;
	}

	public static final void log (final String line) {
		// FIXME
		System.out.println (line);
	}

	public static final void exit (final String line) {
		prt ("# FATAL: " + line);
		System.exit (-1);
	}

	public static void info () {
		prt ("-- consts: ");
		prt ("  K: " + K);
		prt ("  M: " + M);
		prt ("  B: " + B + " aka " + format (B) + " aka " + hfmt (B));
		prt ("  T: " + T + " aka " + format (T) + " aka " + hfmt (T) );
	}

	public static Tuple getNext () {
		return null;
	}

	public static void main (String[] args) {
		for (Element elem : Element.values ()) {
			prt (" elem: " + elem + " type: " + elem.type ());
		}
	}
};

