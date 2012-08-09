
package org.datagen.db.tpch.catalog;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

// Types:
class Type {
	public enum Syllable1 {	STANDARD, SMALL, MEDIUM, LARGE, ECONOMY, PROMO };
	public enum Syllable2 {	ANODIZED, BURNISHED, PLATED, POLISHED, BRUSHED };
	public enum Syllable3 { TIN, NICKEL, BRASS, STEEL, COPPER };

	private static final List<String> tlist = new ArrayList<String> ();

	static {
		List<Syllable1> s1 = new ArrayList<Syllable1> ();
		List<Syllable2> s2 = new ArrayList<Syllable2> ();
		List<Syllable3> s3 = new ArrayList<Syllable3> ();
		for (Syllable1 val: Syllable1.values ()) {	s1.add (val);  }
		for (Syllable2 val: Syllable2.values ()) {	s2.add (val);  }
		for (Syllable3 val: Syllable3.values ()) {	s3.add (val);  }

		Collections.shuffle (s1); 
		Collections.shuffle (s2);
		Collections.shuffle (s3);

		for (Syllable1 v1: Syllable1.values ()) {
			for (Syllable2 v2: Syllable2.values ()) {
				for (Syllable3 v3: Syllable3.values ()) {
					String res = v1 + " " + v2 + " " + v3;
					tlist.add (res);
				}
			}
		}
	}

	public static final int size () {
		return tlist.size ();
	}

	public static final String get (int i) {
		return tlist.get (i);
	}
};

// Containers: 
class Container {

	public enum Syllable1 { SM, LG, MED, JUMBO, WRAP };
	public enum Syllable2 { CASE, BOX, BAG, JAR, PKG, PACK, CAN, DRUM };
	private static final List<String> clist = new ArrayList<String> ();

	static {
		List<Syllable1> s1 = new ArrayList<Syllable1> ();
		List<Syllable2> s2 = new ArrayList<Syllable2> ();

		for (Syllable1 val: Syllable1.values ()) {	s1.add (val);  }
		for (Syllable2 val: Syllable2.values ()) {	s2.add (val);  }

		Collections.shuffle (s1); 
		Collections.shuffle (s2);

		for (Syllable1 v1: Syllable1.values ()) {
			for (Syllable2 v2: Syllable2.values ()) {
				String res = v1 + " " + v2;
				clist.add (res);
			}
		}
	}

	public static final int size () {
		return clist.size ();
	}

	public static final String get (int i) {
		return clist.get (i);
	}
};

public class Dictionary {

	static final Random rng = new Random ();
	static final String space = " ";

	// Dates: 
	public static final String date[] = {
	};

	// p_name: sentence with 5 unique random strings 
	public static final String name[] = {
			"almond", "antique", "aquamarine", "azure", 
			"beige", "bisque", "black", "blanched", "blue", 
			"blush", "brown", "burlywood", "burnished", 
			"chartreuse", "chiffon", "chocolate", "coral", 
			"cornflower", "cornsilk", "cream", "cyan", 
			"dark", "deep", "dim", "dodger", "drab", 
			"firebrick", "floral", "forest", "frosted", 
			"gainsboro", "ghost", "goldenrod", "green", 
			"grey", "honeydew", "hot", "indian", "ivory", 
			"khaki", "lace", "lavender", "lawn", "lemon", 
			"light", "lime", "linen", "magenta", "maroon", 
			"medium", "metallic", "mid- night", "mint", 
			"misty", "moccasin", "navajo", "navy", "olive", 
			"orange", "orchid", "pale", "papaya", "peach", 
			"peru", "pink", "plum", "powder", "puff", "purple", 
			"red", "rose", "rosy", "royal", "saddle", "salmon", 
			"sandy", "seashell", "sienna", "sky", "slate", 
			"smoke", "snow", "spring", "steel", "tan", 
			"thistle", "tomato", "turquoise", "violet", 
			"wheat", "white", "yellow"
	};

	// Segments: 
	public static final String segment[] = {
			"AUTOMOBILE", "BUILDING", "FURNITURE", 
			"MACHINERY", "HOUSEHOLD"
	};

	// Priorities: 
	public static final String priority[] = {
			"1-URGENT", "2-HIGH", "3-MEDIUM", 
			"4-NOT SPECIFIED", "5-LOW"
	};

	// Instructions: 
	public static final String instruction[] = {
			"DELIVER IN PERSON", "COLLECT COD", 
			"NONE", "TAKE BACK RETURN"
	};

	// Modes: 
	public static final String mode[] = {
			"REG AIR", "AIR", "RAIL", "SHIP", 
			"TRUCK", "MAIL", "FOB"
	};

	// 
	// Nouns: 
	public static final String noun[] = {
			"foxes", "instructions", "asymptotes", "sauternes", 
			"attainments", "forges", "warhorses", "pearls", 
			"gifts", "decoys", "escapades", "ideas", "dependencies", 
			"courts", "warthogs", "somas", "braids", "dugouts", 
			"tithes", "sheaves", "realms", "theodolites", "excuses", 
			"dolphins", "frets", "Tiresias'", "hockey", "players", 
			"notornis", "waters", "depths","pains", "pinto", "beans", 
			"platelets", "multipliers", "dinos", "patterns", "frays", 
			"epitaphs", "orbits", "sentiments", "grouches"
	};

	// Verbs: 
	public static final String verb[] = {
			"sleep", "wake", "are", "haggle", "nag", 
			"use", "affix", "detect", "integrate", 
			"nod", "was", "lose", "solve", "thrash", 
			"promise", "hinder", "print", "x-ray", 
			"eat", "grow", "impress", "poach", 
			"serve", "run", "snooze", "doze", 
			"unwind", "play", "hang", "believe", 
			"cajole", "boost", "maintain", "sublate", 
			"engage", "breach", "mold", "dazzle", 
			"kindle", "doubt" 
	};

	// Adjectives: 
	public static final String adjective[] = {
			"furious", "sly", "careful", "quick", 
			"fluffy", "slow", "ruthless", "thin", 
			"close", "daring", "brave", "stealthy", 
			"enticing", "idle", "busy", "final", 
			"ironic", "even", "silent", "blithe", 
			"quiet", "dogged", "permanent", "regular", 
			"bold"
	};

	// Adverbs: 
	public static final String adverb[] = {
			"sometimes", "always", "never", "slyly", 
			"carefully", "blithely", "fluffily", 
			"slowly", "quietly", "thinly", "closely", 
			"doggedly", "bravely", "stealthily", 
			"permanently", "idly", "busily", "regularly", 
			"ironically", "evenly", "boldly", "furiously", 
			"quickly", "ruthlessly", "daringly", "enticingly", 
			"finally", "silently"
	};

	// Prepositions: 
	public static final String preposition[] = {
			"about", "after", "among", "before", 
			"besides", "despite", "from", "into", 
			"outside", "through", "under", "without", 
			"above", "against", "around", "behind", 
			"between", "during", "in", "place", "of", 
			"near", "over", "throughout", "until", 
			"with" , "according to", "along", "at", 
			"beneath", "beyond", "except", "inside of", 
			"past", "to", "up", "within", "across", 
			"alongside", "of", "atop", "beside", "by", 
			"for", "instead of", "on", "since", 
			"toward", "upon"
	};

	// Auxillaries: 
	public static final String auxiliary[] = {
			"do", "may", "might", "shall", "will", 
			"would", "can", "could", "should", 
			"ought to", "must", "will have to", 
			"shall have to", "could have to",
			"could have to", "should have to",
			"must have to", "need to", "try to"
	};

	public static final String terminator[] = {
			".", ";", ": ", "?", "!", "-"
	};

	public static int rnd (int lim) {
		return (int) (rnd_dbl () * lim);
	}

	public static double rnd_dbl () {
		return rng.nextDouble ();
	}

	public static String pad () {
		return space;
	}

	public static String verb () {
		int indx = rnd (verb.length);
		return verb[indx] + pad ();
	}

	public static String preposition () {
		int indx = rnd (preposition.length);
		return preposition[indx] + pad ();
	}

	public static String adjective () {
		int indx = rnd (adjective.length);
		return adjective[indx] + pad ();
	}

	public static String adverb () {
		int indx = rnd (adverb.length);
		return adverb[indx] + pad ();
	}

	public static String auxiliary () {
		int indx = rnd (auxiliary.length);
		return auxiliary[indx] + pad ();
	}

	public static String noun () {
		int indx = rnd (noun.length);
		return noun[indx] + pad ();
	}

	public static String noun_phrase () {
		String res;
		int opt = rnd (4);
		if (opt == 0) {
			res = noun ();
		}
		else if (opt == 1) {
			res = adjective () + noun ();
		}
		else if (opt == 1) {
			res = adjective () + adjective () + noun ();
		}
		else {
			res = adverb () + adjective () + noun ();
		}
		return res;
	}

	public static String verb_phrase () {
		String res;
		int opt = rnd (3);
		if (opt == 0) {
			res = auxiliary () + verb ();
		}
		else if (opt == 1) {
			res = verb () + adverb ();
		}
		else {
			res = auxiliary () + verb () + adverb ();
		}
		return res;
	}

	public static String prepositional_phrase () {
		String res = preposition ();
		res += "the " ;
		res += adverb ();
		return res;
	}

	public static String sentence () {
		StringBuilder res = new StringBuilder (64);
		int opt = rnd (4);
		if (opt == 0) {
			res.append (noun_phrase ())
			   .append (verb_phrase ());
		}
		else if (opt == 1) {
			res.append (noun_phrase ())
			   .append (verb_phrase ())
			   .append (prepositional_phrase ());
		}
		else if (opt == 2) {
			res.append (noun_phrase ())
			   .append (verb_phrase ())
			   .append (noun_phrase ());
		}
		else if (opt == 3) {
			res.append (noun_phrase ())
			   .append (prepositional_phrase ())
			   .append (verb_phrase ())
			   .append (noun_phrase ());
		}
		else {
			res.append (noun_phrase ())
			   .append (prepositional_phrase ())
			   .append (verb_phrase ())
			   .append (prepositional_phrase ());
		}
		res.append (terminator ());
		return res.toString();
	}

	public static String text () {
		StringBuilder res = new StringBuilder (90);
		int opt = rnd (1);
		if (opt == 0) {
			res.append (sentence ());
		}
		else {
			res.append (text ())
			   .append (pad ())
			   .append (sentence ());
		}
		return res.toString ();
	}

	public static String text (int lim) {
		String res = text ();
		int len = 0;
		while ((len = res.length ()) < lim) {
			res += sentence ();
		}
		if (len > lim) {
			char delim = res.charAt (len-1);
			return (res.substring (0, res.lastIndexOf (" ")) + delim);
		}
		return res;
	}

	public static String phrase () {
		String res;
		int opt = rnd (3);
		if (opt == 0) {
			res = phrase () + verb () + terminator ();
		}
		else if (opt == 1) {
			res = sentence ();
		}
		else {
			res = text ();
		}
		return res;
	}

	public static String name () {
		StringBuilder res = new StringBuilder (36);
		int len = name.length;
		res.append (name[rnd(len)])
		   .append (pad())
		   .append (name[rnd(len)])
		   .append (pad())
		   .append (name[rnd(len)])
		   .append (pad())
		   .append (name[rnd(len)])
		   .append (pad())
		   .append (name[rnd(len)]);
		return res.toString ();
	}

	public static String type () {
		return Type.get (rnd (Type.size() - 1 ));
	}

	public static String container () {
		return Container.get (rnd (Container.size() - 1));
	}

	public static String priority () {
		return priority[ rnd (priority.length) ];
	}

	public static String instruction () {
		return instruction[ rnd (instruction.length) ];
	}

	public static String mode () {
		return mode[ rnd (mode.length) ];
	}

	public static String segment () {
		return segment[ rnd (segment.length) ];
	}

	public static String terminator () {
		return terminator[ rnd (terminator.length) ];
	}

	public static String init () {
		return "";
	}

	public static String bag () {
		String res = init ();
		res += noun ();
		res += verb ();
		res += adjective ();
		res += preposition ();
		res += auxiliary ();
		res += adjective ();
		res += adverb ();
		res += noun (); 
		res = res.trim (); // trim before punctuation
		res += terminator ();
		return res;
	}

	public static void info () {
		prt (" -- Name: " + name.length);
		prt (" -- Type: " + Type.size ());
		prt (" -- Container: " + Container.size ());
		prt (" -- Segment: " + segment.length);
		prt (" -- Priority: " + priority.length);
		prt (" -- Instruction: " + instruction.length);
		prt (" -- Mode: " + mode.length);
		prt (" -- Noun: " + noun.length);
		prt (" -- verb: " + verb.length);
		prt (" -- adjective: " + adjective.length);
		prt (" -- adverb: " + adverb.length);
		prt (" -- preposition: " + preposition.length);
		prt (" -- auxiliary: " + auxiliary.length);
		prt (" -- terminator: " + terminator.length);
		prt ();
	}

	public static final void prt (String line) {
		System.out.println (line);
	}

	public static final void prt () {
		System.out.println ();
	}
};
