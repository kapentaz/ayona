package com.ayona.context;

import com.ayona.TypeR;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 멀티스레드 환경을 위한 {@link ConcurrentHashMap} 기반 Context 구현체
 */
public class ContextSupport implements Context {

	private final Map<String, Object> variables = new ConcurrentHashMap<>();

	@Override
	public void set(String name, Object value) {
		variables.put(name, value);
	}

	@Override
	public String[] getNames() {
		return this.variables.keySet().toArray(new String[this.variables.size()]);
	}

	@Override
	public Object get(String name) {
		return variables.get(name);
	}

	@Override
	public Object remove(String name) {
		return variables.remove(name);
	}

	@Override
	public boolean has(String name) {
		return variables.containsKey(name);
	}

	@Override
	public <T> T get(String name, Class<T> type) {
		return type.cast(variables.get(name));
	}

	@Override
	public <T> T get(String name, TypeR<T> type) {
		return type.getType().cast(variables.get(name));
	}

}
