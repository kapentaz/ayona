package com.ayona;

import com.ayona.context.Context;
import com.ayona.context.ContextSupplier;

/**
 * 호출할 정보를 의미한다.
 */
public interface CallInfo {

	ContextSupplier<String> getId();

	String toString(Context context);

}
