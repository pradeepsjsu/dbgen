package org.datagen.db.gmm.schema;

public class Range {
	int start, end;

	public Range (int start, int end) {
		this.start = start;
		this.end = end;
	}

	public int min () {
		return start;
	}

	public int max () {
		return end-1;
	}

	public Integer count () {
		return null;	
	}

	public int length () {
		return (end - start);
	}
};


