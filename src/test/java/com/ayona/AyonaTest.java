package com.ayona;

import org.junit.Test;
import org.springframework.http.MediaType;

public class AyonaTest {

	@Test
	public void test() throws Exception {
		CallStream.create()
				.add(() -> {
					ApiCallInfo<String, String> info = new ApiCallInfo<>();
					info.setId(ctx -> "기본 호출 테스트");
					info.get(ctx -> "http://localhost");
					info.setMediaType(MediaType.APPLICATION_JSON);
					info.setReq(ctx -> "");
					info.setRes((ctx, res) -> {
					});
					return info;
				})
				.addAsync(() -> {
					ApiCallInfo<String, String> info = new ApiCallInfo<>();
					info.setId(ctx -> "비동기 호출 테스트");
					info.get(ctx -> "http://localhost");
					info.setMediaType(MediaType.APPLICATION_JSON);
					info.setReq(ctx -> "");
					info.setRes((ctx, res) -> {
					});
					return info;
				})
				.delay(1000)
				.addAsync(() -> {
					ApiCallInfo<String, String> info = new ApiCallInfo<>();
					info.setId(ctx -> "비동기 호출 테스트2");
					info.get(ctx -> "http://localhost");
					info.setMediaType(MediaType.APPLICATION_JSON);
					info.setReq(ctx -> "");
					info.setRes((ctx, res) -> {
					});
					return info;
				})
				.loop(1)
				.run();
	}
}