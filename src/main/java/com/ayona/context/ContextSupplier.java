package com.ayona.context;

import java.util.function.Supplier;

/**
 * {@link Context} 정보를 제공하는 {@link Supplier}
 */
@FunctionalInterface
public interface ContextSupplier<T> {

	T get(Context ctx);

}
