package com.ayona.context;

/**
 * Caller 를 통해서 실행된 정보를 저장하거나, 필요한 공유 변수를 정의해서 사용할 수 있다.
 */
public interface Context {

	void setVariable(String name, Object value);

	String[] getVariableNames();

	Object getVariable(String name);

	<T> T getVariable(String name, Class<T> type);

	Object removeVariable(String name);

	boolean hasVariable(String name);

}
