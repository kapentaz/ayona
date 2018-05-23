package com.ayona.context;

import com.ayona.TypeR;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ContextSupportTest {

	@Test
	public void genericContextDataTest() throws Exception {
		ContextSupport contextSupport = new ContextSupport();
		contextSupport.set("test", Arrays.asList(LocalDate.of(1988, 1, 1), LocalDate.of(2018, 12, 31)));

		List<LocalDate> test = contextSupport.get("test", new TypeR<List<LocalDate>>() {
		});
		test.forEach(i -> {
			log.info("Type:{}, Value:{}", i.getClass(), i);
			Assert.assertEquals(LocalDate.class, i.getClass());
		});
	}

}