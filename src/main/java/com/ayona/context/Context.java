package com.ayona.context;

/**
 * Caller 를 통해서 실행된 정보를 저장하거나, 필요한 공유 변수를 정의해서 사용할 수 있다.
 */
public interface Context {

	void set(String name, Object value);

	Object get(String name);

	<T> T get(String name, Class<T> type);

	Object remove(String name);

	String[] getNames();

	boolean has(String name);

	default String concat(String name, String value) {
		return get(name) + value;
	}

}
