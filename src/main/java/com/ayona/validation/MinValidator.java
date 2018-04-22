package com.ayona.validation;

public class MinValidator extends AbstractValidator {

	private final int min;

	public MinValidator(Object target, int min) {
		super(target);
		this.min = min;
	}

	@Override
	public void validate() {
		new SizeValidator(target, min, -1).validate();
	}

}
