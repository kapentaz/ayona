package com.ayona;

import com.ayona.util.ExceptionUtil;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Optional;

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
				.error((ctx, error) -> {
					Optional<String> bodyAsString = ExceptionUtil.getResponseBodyAsString(error);
					bodyAsString.ifPresent(s -> log.info("{}", s));
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

		CallStream.create()
				.add(() -> first.next(second))
				.run();
	}

	@Test
	public void loop() throws Exception {
		ApiCallInfo<String, String> callInfo = ApiCallInfo.<String, String>builder()
				.id(ctx -> "호출 테스트2")
				.get(ctx -> "http://localhost?2=2")
				.req(ctx -> "")
				.res((ctx, res) -> {
					res = "{\"data\":123123123}";
					ctx.set("mainDealNo", JsonPath.parse(res).read("$.data"));
				})
				.build();

		ApiCallInfo<String, String> loop = callInfo.copy(100);
		loop.children().forEach(System.out::println);
	}
}