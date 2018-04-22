package com.ayona.validation;

import java.util.Collection;
import java.util.Map;

public class SizeValidator extends AbstractValidator {

	private final int min;
	private final int max;

	public SizeValidator(Object target, int min, int max) {
		super(target);
		this.min = min;
		this.max = max;
	}

	@Override
	public void validate() {
		if (target instanceof String) {
			isValid(((String) target).length());
		} else if (target instanceof Collection) {
			isValid(((Collection) target).size());
		} else if (target instanceof Map) {
			isValid(((Map) target).size());
		} else if (isArray()) {
			isValid(((Object[]) target).length);
		}
	}

	private void isValid(int size) {
		if ((size < min && min > -1) || (size > max && max > -1)) {
			String message;
			if (min > -1 && max > -1) {
				message = "must be between " + min + " and " + max;
			} else if (min > -1 && max < 0) {
				message = "must be greater than or equal to " + min;
			} else if (min < 0 && max > -1) {
				message = "must be less than or equal to " + max;
			} else {
				message = "illegal argument";
			}
			throw new NotValidStateException(message);
		}
	}

}
