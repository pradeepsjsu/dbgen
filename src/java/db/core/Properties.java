package org.datagen.db.core;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.hadoop.conf.Configuration;

public class Properties {

	HashMap<String,String> map;

	public Properties (Configuration conf) {
		map = new HashMap<String,String> ();
		Iterator iter = conf.iterator ();
		while (iter.hasNext ()) {
			Map.Entry<String,String> entry = (Map.Entry<String,String>) (iter.next ());
			String name = entry.getKey ();
			String value = entry.getValue ();
			map.put (name, value);
			//System.out.println ("{" + name + ": '" + value + "'}");
		}
	}

	public String get (String name) {
		return map.get (name);
	}

	public Integer getInt (String name) {
		String res = map.get (name);
		try {
			return Integer.parseInt (res);
		} catch (NumberFormatException ne) {
			return null;
		}
	}

	public boolean exists (String name) {
		boolean res = false;
		if (map.get (name) != null) {
			res = true;
		}
		return res;
	}

	public int size () {
		return map.size ();
	}
};
