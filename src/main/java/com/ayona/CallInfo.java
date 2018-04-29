package com.ayona;

import com.ayona.context.Context;
import com.ayona.context.ContextSupplier;

import java.util.stream.Stream;

/**
 * 호출할 정보를 의미한다.
 */
public interface CallInfo {

	ContextSupplier<String> getId();

	String toString(Context context);

	/**
	 * 연결된 모든 CallInfo를 반환한다.
	 * <p>CallInfo는 다른 CallInfo와 연결할 수 있으며, 순서대로 호출되어야 한다.
	 */
	Stream<CallInfo> children();

}
