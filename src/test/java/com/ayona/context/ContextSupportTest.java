package com.ayona.context;

import com.ayona.TypeR;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class ContextSupportTest {

	@Test
	public void get() throws Exception {
		ContextSupport contextSupport = new ContextSupport();
		contextSupport.set("test", Arrays.asList(1, 2, 3));

		List<Integer> test = contextSupport.get("test", new TypeR<List<Integer>>() {
		});
		test.forEach(i -> log.info("{}", i));
	}

}