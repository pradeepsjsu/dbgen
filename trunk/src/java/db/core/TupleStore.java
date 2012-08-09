package org.datagen.db.core;

// inmemory store for tuples

public class TupleStore {

	final int MAX_LEN = 12; 
	final Tuple[] data = new Tuple[MAX_LEN]; 
	int in_cursor;
	int out_cursor;
	int limit;

	public TupleStore () {
		in_cursor = 0;
		out_cursor = 0;
		limit = 0;
	}

	public boolean add (Tuple tuple) {
		boolean res = false;
		if (!isFull ()) {
			data[in_cursor++] = tuple;
			limit = in_cursor;
			res = true;
		}
		return res;
	}

	public void clear () {
		for (int i = 0; i < limit; i++) {
			data[i] = null;
		}
		reset ();
	}

	public void reset () {
		in_cursor = 0;
		out_cursor = 0;
		limit = 0;
	}

	public boolean isEmpty () {
		return !isFull();
	}

	public Tuple getNext () {
		if (hasMore ()) {
			return data[out_cursor++];
		}
		return null;
	}

	public boolean isFull () {
		boolean res = true;
		if (in_cursor < MAX_LEN) {
			res = false;
		}
		return res;
	}

	public boolean hasMore () {
		boolean res = false;
		if (out_cursor < limit) {
			res = true;
		}
		return res;
	}
};
