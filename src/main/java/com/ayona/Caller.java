package com.ayona;

import com.ayona.context.Context;

/**
 * {@link CallInfo}를 실행하는 역활
 */
public interface Caller<T extends CallInfo> {

	void execute(T t, Context context);

}
