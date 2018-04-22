package com.ayona.validation;

public class NotNullValidator extends AbstractValidator {

	public NotNullValidator(Object target) {
		super(target);
	}

	@Override
	public void validate() {
		if (target == null) {
			throw new NotValidStateException("must be not null");
		}
	}

}
