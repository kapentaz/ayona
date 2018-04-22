package com.ayona.validation;

public abstract class AbstractValidator implements Validator {

	protected final Object target;
	private boolean isArray;

	public AbstractValidator(Object target) {
		this.target = target;
		if (target != null && target.getClass().isArray()) {
			this.isArray = true;
		}
	}

	public Object getTarget() {
		return target;
	}

	public boolean isArray() {
		return isArray;
	}

}
