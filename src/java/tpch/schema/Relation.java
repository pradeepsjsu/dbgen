
package org.datagen.tpch.schema;
import org.datagen.tpch.util.Tuple;

public interface Relation {
	public void init ();
	public void reset ();
	public void seek (long offset);
	public void close ();
	public Tuple getNext () throws Exception;
};
