package com.ayona;

import org.junit.Test;

public class AyonaTest {

	@Test
	public void json_형태의_문자열로_파라미터넘기기() throws Exception {
		CallStream.create()
				.add(() -> ApiCallInfo.<String, String>builder()
						.id(ctx -> "json 형태의 문자열로 파라미터 넘기기")
						.post(ctx -> "http://localhost:8080/sample")
						.req(ctx -> "{\"age\":123,\"name\":\"ayona\"}")
						.res((ctx, res) -> {
							/*assertThat(res, hasJsonPath("$.name", equalTo("ayona")));
							assertThat(res, hasJsonPath("$.age", equalTo(123)));*/
						}).build())
				.run();
	}

	@Test
	public void name() throws Exception {
		CallStream.create()
				.run();

	}

	@Test
	public void test() throws Exception {
		CallStream.create()
				.add(() -> ApiCallInfo.<String, String>builder()
						.id(ctx -> "기본 호출 테스트")
						.post(ctx -> "http://localhost")
						.req(ctx -> "")
						.res((ctx, res) -> {
						}).build())
				.addAsync(() -> ApiCallInfo.<String, String>builder()
						.id(ctx -> "비동기 호출 테스트")
						.post(ctx -> "http://localhost")
						.req(ctx -> "")
						.res((ctx, res) -> {
						}).build())
				.delay(1000)
				.addAsync(() -> ApiCallInfo.<String, String>builder()
						.id(ctx -> "비동기 호출 테스트2")
						.post(ctx -> "http://localhost")
						.req(ctx -> "")
						.res((ctx, res) -> {
						}).build())
				.loop(1)
				.run();
	}
}