package edu.boun.swe574.fsn.backend.ws.util;

import java.io.Serializable;
import java.util.Map;

/**
 * An Map.Entry implementation.
 * @param <K> Type of the key of the entry.
 * @param <V> Type of the value of the entry.
 * @author Canay ÖZEL <canay.ozel@gmail.com>
 * @version 1.0 created on 20.Haz.2010 02:51:00
 */
public class KeyValuePair<K, V> implements Map.Entry<K, V>, Serializable {

	private static final long serialVersionUID = 1123123L;
	
	/**
     * Key of the entry.
     */
    K key;
    /**
     * Value of the entry.
     */
    V value;

    /**
     * Creates an instance of this class.
     * @param key key for the entry.
     * @param value Value for the entry.
     */
    public KeyValuePair(K key,V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public V setValue(V value) {
        this.value = value;
        return value;
    }
}
