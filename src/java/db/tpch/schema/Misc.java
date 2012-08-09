
package org.datagen.db.tpch.schema;
import org.datagen.db.core.Tuple;
import org.datagen.db.tpch.catalog.Dictionary;

public class Misc {

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
	static final Dictionary D = new Dictionary ();

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

	static int keylen = 17;
	static int wordlen = 3;

	public static final String key () {
		int len;
		len = keylen + D.rnd (3);
		int max = ck.length ();
		char res[] = new char[len+3];
		res[0] = ' ';
		res[1] = ' ';
		res[2] = ' ';
		for (int i = 3; i < len; i++) {
			int indx = D.rnd (max);
			res[i] = ck.charAt(indx);
		}
		return new String (res);
	}

	public static final String word () {
		int indx = D.rnd (word.length);
		return word[indx] + " ";
	}

	private static final String digit () {
		int pos = D.rnd (numerals.length());
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
		return alphabet.charAt ( D.rnd (alphabet.length()) );
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

	public static final String phrase (int maxlen) {
		StringBuilder res = new StringBuilder (maxlen);
		while (res.length () < maxlen) {
			res.append (word());
		}
		return res.toString ();
	}

	public static final String nextWord () {
		int len = wordlen + D.rnd (7);
		String res = "";
		for (int i = 1; i < len; i++) {
			res += word();
		}
		return res;
	}
};


