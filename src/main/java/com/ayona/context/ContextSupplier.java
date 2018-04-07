package com.ayona.context;

@FunctionalInterface
public interface ContextSupplier<T> {

	T get(Context ctx);

}
