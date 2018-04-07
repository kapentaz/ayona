package com.ayona.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 멀티스레드 환경을 위한 {@link ConcurrentHashMap} 기반 Context 구현체
 */
public class ContextSupport implements Context {

	private final Map<String, Object> variables = new ConcurrentHashMap<>();

	@Override
	public void setVariable(String name, Object value) {
		variables.put(name, value);
	}

	@Override
	public String[] getVariableNames() {
		return this.variables.keySet().toArray(new String[this.variables.size()]);
	}

	@Override
	public Object getVariable(String name) {
		return variables.get(name);
	}

	@Override
	public Object removeVariable(String name) {
		return variables.remove(name);
	}

	@Override
	public boolean hasVariable(String name) {
		return variables.containsKey(name);
	}

	public <T> T getVariable(String name, Class<T> type) {
		return type.cast(variables.get(name));
	}

}
