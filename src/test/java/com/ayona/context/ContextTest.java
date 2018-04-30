package com.ayona.context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class ContextTest {

	private Context context = new ContextSupport();

	@Before
	public void setUp() throws Exception {
		context.set("mainDealNo", 123456789);
	}

	@Test
	public void format() throws Exception {
		Assert.assertEquals("/api/deals/123456789", context.format("/api/deals/{mainDealNo}", "$.mainDealNo"));
	}

	@Test
	public void concat() throws Exception {
		Assert.assertEquals("테스트1970-01-01123456789", context.concat("테스트", LocalDate.of(1970, 1, 1), "$.mainDealNo"));
	}
}