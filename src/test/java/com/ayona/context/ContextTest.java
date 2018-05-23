package com.ayona.context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class ContextTest {

	private Context context = new ContextSupport();

	@Before
	public void setUp() throws Exception {
		context.set("productNo", 123456789);
	}

	@Test
	public void concat() throws Exception {
		Assert.assertEquals("테스트1970-01-01123456789", context.concat("테스트", LocalDate.of(1970, 1, 1), "$.productNo"));
	}

	@Test
	public void format() throws Exception {
		Assert.assertEquals("/api/products/123456789", context.format("/api/products/{productNo}", "$.productNo"));
	}

	@Test
	public void replaceBraces() throws Exception {
		Assert.assertEquals("/api/products", context.replaceBraces("/api/products"));
		Assert.assertEquals("/api/products/12345/details/{detailNo}", context.replaceBraces("/api/products/{productNo}/details/{detailNo}", 12345, null));
		Assert.assertEquals("/api/products/12345/details/6789", context.replaceBraces("/api/products/{productNo}/details/{detailNo}", 12345, 6789));
	}
}