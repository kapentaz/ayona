package com.ayona;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ApiCallInfoTest {

	@Test
	public void name() throws Exception {

		ApiCallInfo<String, String> first = ApiCallInfo.<String, String>builder()
				.id(ctx -> "호출 테스트1")
				.get(ctx -> "http://localhost")
				.req(ctx -> "")
				.res((ctx, res) -> {
				})
				.build();

		ApiCallInfo<String, String> second = ApiCallInfo.<String, String>builder()
				.id(ctx -> "호출 테스트2")
				.get(ctx -> "http://localhost?2=2")
				.req(ctx -> "")
				.res((ctx, res) -> {
					res = "{\"data\":123123123}";
					ctx.set("mainDealNo", JsonPath.parse(res).read("$.data"));
				})
				.build();

		first.add(second);

		CallStream.create()
				.add(() -> first)
				.run();
	}

}