
package org.datagen.db.core;

import java.util.ArrayList;

public class Tuple {

	final Object[] buff;
	int cursor;

	public Tuple () {
		buff = new Object[13];
		cursor = 0;
	}

	public Tuple (Object... src) {
		buff = new Object[src.length];
		System.arraycopy (src, 0, buff, 0, src.length);
		cursor = buff.length;
	}

	public final Object get (int slot) {
		ensure_valid (slot);
		return buff[slot];
	}

	public final boolean store (final Object obj) {
		ensure_valid ();
		buff[cursor++] = obj;
		return true;
	}

	private final void ensure_valid () {
		if (buff == null) {
			throw new RuntimeException ("tuple buffer uninitialized..");
		}
	}

	private final void ensure_valid (int slot) {
		if (slot >= cursor || cursor >= buff.length) {
			throw new RuntimeException ("tuple buffer -- null pointer exception..");
		}
	}

	public final String toString () {
		ensure_valid ();
		StringBuilder res = new StringBuilder (128);
		for (int i = 0; i < cursor; i++) {
			if (buff[i] != null) {
				res.append (buff[i].toString());
			}
			// null value is encoding by nothing..
			res.append ("|");
		}
		return res.toString ();
	}
};
