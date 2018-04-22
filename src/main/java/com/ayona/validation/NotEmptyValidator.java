package com.ayona.validation;

public class NotEmptyValidator extends AbstractValidator {

	public NotEmptyValidator(Object target) {
		super(target);
	}

	@Override
	public void validate() {
		new SizeValidator(target, 1, -1).validate();
	}

}
