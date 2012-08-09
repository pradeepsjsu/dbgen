package org.datagen.db.core;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Die {
	final Double[] wt;
	public final int NUM_FACES;

	public Die (Double[] wt) {
		this.wt = wt;
		List<Double> list = new ArrayList<Double>();
		for (int i = 0; i < wt.length; i++) {
			list.add (wt[i]);
		}
		Collections.sort (list);
		for (int i = 0; i < list.size(); i++) {
			wt[i] = list.get (i);
		}
		this.NUM_FACES = wt.length;
	}

	public void prt () {
		for (int i = 0; i < NUM_FACES; i++) {
			Util.prt (i + ": " + wt[i]);
		}
	}

	public int roll (Random rng) {
		double threshold = rng.nextDouble ();
		double sum = 0;
		for (int i = 0; i < NUM_FACES; i++) {
			sum += wt[i];
			if (sum >= threshold) {
				return i;
			}
		}
		return NUM_FACES-1;
	}

	public int size () {
		return NUM_FACES;
	}
};
