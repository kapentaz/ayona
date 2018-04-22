package com.ayona.validation;

import org.junit.Test;

import static com.ayona.validation.ValidChecker.target;

public class ValidCheckerTest {

	@Test
	public void name() throws Exception {

		String s = "123456";

		target(s).notNull().min(1).max(7).size(1, 7).validate();
	}
}