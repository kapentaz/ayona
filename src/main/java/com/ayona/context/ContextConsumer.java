package com.ayona.context;

import java.util.function.Consumer;

/**
 * {@link Context} 정보를 제공하는 {@link Consumer}
 */
@FunctionalInterface
public interface ContextConsumer<T> {

	void accept(Context ctx, T t);

}
