package com.ayona;

import org.springframework.core.GenericTypeResolver;

public abstract class TypeR<T> {

	private final Class<T> type;

	@SuppressWarnings("unchecked")
	public TypeR() {
		this.type = (Class<T>) GenericTypeResolver.resolveTypeArgument(this.getClass(), TypeR.class);
	}

	public Class<T> getType() {
		return type;
	}

}
