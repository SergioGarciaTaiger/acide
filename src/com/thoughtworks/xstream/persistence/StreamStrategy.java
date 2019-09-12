package com.thoughtworks.xstream.persistence;

import java.util.Iterator;

/**
 * A key to filename and vice-versa strategy interface.
 * 
 * @author Guilherme Silveira
 */

@SuppressWarnings({"rawtypes"})
public interface StreamStrategy {

	Iterator iterator();

	int size();

	Object get(Object key);

	Object put(Object key, Object value);

	Object remove(Object key);

}