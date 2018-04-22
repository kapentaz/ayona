package com.ayona.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidChecker {

	private List<Validator> validators = new ArrayList<>();

	private Object target;


	private ValidChecker(Object target) {
		this.target = target;
	}

	public static ValidChecker target(Object target) {
		return new ValidChecker(target);
	}

	public ValidChecker notNull() {
		validators.add(new NotNullValidator(target));
		return this;
	}

	public ValidChecker notEmpty() {
		validators.add(new NotEmptyValidator(target));
		return this;
	}

	public ValidChecker min(int min) {
		validators.add(new MinValidator(target, min));
		return this;
	}

	public ValidChecker max(int max) {
		validators.add(new MaxValidator(target, max));
		return this;
	}

	public ValidChecker size(int min, int max) {
		validators.add(new SizeValidator(target, min, max));
		return this;
	}

	public void validate() {
		validators.forEach(Validator::validate);
	}

}
