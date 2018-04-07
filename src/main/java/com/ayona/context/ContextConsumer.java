package com.ayona.context;

@FunctionalInterface
public interface ContextConsumer<T> {

	void accept(Context ctx, T t);

}
