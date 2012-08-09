package org.datagen.db.core;

public class Counter {
	long cursor;
	int step;

	public Counter () {
		cursor = 0;
		step = 1;
	}

	public Counter (int step) {
		this.cursor = 0;
		this.step = step;
	}

	public Counter (long offset) {
		this.cursor = offset;
		this.step = 1;
	}

	public Counter (long offset, int step) {
		this.cursor = offset;
		this.step = step;
	}

	public long current () {
		return cursor;
	}

	public long next () {
		cursor += step;
		return cursor;
	}

	public String toString () {
		return "" + cursor;
	}
};

