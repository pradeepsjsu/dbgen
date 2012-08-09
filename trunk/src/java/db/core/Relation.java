
package org.datagen.db.core;

public interface Relation {
	public void init (Properties map);
	//public void reset ();
	public void seek (long offset);
	public void close ();
	public Tuple getNext () throws Exception;
};
