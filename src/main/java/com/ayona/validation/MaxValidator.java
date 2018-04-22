package com.ayona.validation;

public class MaxValidator extends AbstractValidator {

	private final int max;

	public MaxValidator(Object target, int max) {
		super(target);
		this.max = max;
	}

	@Override
	public void validate() {
		new SizeValidator(target, -1, max).validate();
	}

}
