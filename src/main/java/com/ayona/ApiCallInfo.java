package com.ayona;

import com.ayona.context.Context;
import com.ayona.context.ContextConsumer;
import com.ayona.context.ContextSupplier;
import com.ayona.util.ExceptionUtil;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * API 호출 정보를 정의한다.
 */
public class ApiCallInfo<I, O> implements CallInfo {

	@Getter
	private final List<CallInfo> callInfos = new ArrayList<>();
	@Getter
	private ContextSupplier<String> id;
	@Getter
	private ContextSupplier<String> uri;
	@Getter
	private HttpMethod method;
	@Getter
	private MediaType mediaType;
	private ContextSupplier<I> req;
	private ContextConsumer<O> res;
	@Getter
	private Class<O> resType;
	private ContextConsumer<RestClientException> error;

	private ApiCallInfo(ApiCallInfo<I, O> apiCallInfo) {
		this.id = apiCallInfo.id;
		this.uri = apiCallInfo.uri;
		this.method = apiCallInfo.method;
		this.mediaType = apiCallInfo.mediaType;
		this.req = apiCallInfo.req;
		this.res = apiCallInfo.res;
		this.resType = apiCallInfo.resType;
		this.error = apiCallInfo.error;
	}

	private ApiCallInfo(Builder<I, O> builder) {
		this.id = builder.id;
		this.uri = builder.uri;
		this.method = builder.method;
		this.mediaType = builder.mediaType;
		this.req = builder.req;
		this.res = builder.res;
		this.resType = builder.resType;
		this.error = builder.error;
	}

	public static <I, O> ApiCallInfo.Builder<I, O> builder() {
		return new ApiCallInfo.Builder<>();
	}

	public ApiCallInfo<I, O> next(ApiCallInfo callInfo) {
		callInfos.add(callInfo);
		return this;
	}

	public Optional<ContextSupplier<I>> getReq() {
		return Optional.ofNullable(req);
	}

	public Optional<ContextConsumer<O>> getRes() {
		return Optional.ofNullable(res);
	}

	public Optional<ContextConsumer<RestClientException>> getError() {
		return Optional.ofNullable(error);
	}

	public ApiCallInfo<I, O> copy() {
		return new ApiCallInfo<>(this);
	}

	public ApiCallInfo<I, O> copy(int count) {
		List<ApiCallInfo<I, O>> collect = IntStream.rangeClosed(1, count)
				.mapToObj(i -> this.copy())
				.collect(Collectors.toList());
		collect.forEach(this::next);
		return this;
	}

	@Override
	public Stream<CallInfo> children() {
		return Stream.concat(
				Stream.of(this),
				callInfos.stream().flatMap(CallInfo::children));
	}

	@Override
	public String toString(Context context) {
		return "[" + mediaType + ", " + method + "] " + uri.get(context);
	}

	/**
	 * ApiCallInfo 생성을 위한 Builder 클래스
	 */
	public static class Builder<I, O> {

		private static final Logger LOG = LoggerFactory.getLogger(Builder.class);

		private ContextSupplier<String> id;
		private ContextSupplier<String> uri;
		private HttpMethod method;
		private MediaType mediaType = MediaType.APPLICATION_JSON;
		private ContextSupplier<I> req;
		private ContextConsumer<O> res;
		private Class<O> resType;
		private ContextConsumer<RestClientException> error = (ctx, error) ->
				ExceptionUtil.getResponseBodyAsString(error).ifPresent(res -> LOG.error("response body: {}", res));

		private Builder() {
		}

		private Builder(Builder<I, O> builder) {
			this.id = builder.id;
			this.uri = builder.uri;
			this.method = builder.method;
			this.mediaType = builder.mediaType;
			this.req = builder.req;
			this.res = builder.res;
			this.resType = builder.resType;
			this.error = builder.error;
		}

		public ApiCallInfo.Builder<I, O> id(ContextSupplier<String> id) {
			this.id = id;
			return this;
		}

		public ApiCallInfo.Builder<I, O> get(ContextSupplier<String> uri) {
			this.uri = uri;
			this.method = HttpMethod.GET;
			return this;
		}

		public ApiCallInfo.Builder<I, O> post(ContextSupplier<String> uri) {
			this.uri = uri;
			this.method = HttpMethod.POST;
			return this;
		}

		public ApiCallInfo.Builder<I, O> put(ContextSupplier<String> uri) {
			this.uri = uri;
			this.method = HttpMethod.PUT;
			return this;
		}

		public ApiCallInfo.Builder<I, O> delete(ContextSupplier<String> uri) {
			this.uri = uri;
			this.method = HttpMethod.DELETE;
			return this;
		}

		public ApiCallInfo.Builder<I, O> head(ContextSupplier<String> uri) {
			this.uri = uri;
			this.method = HttpMethod.HEAD;
			return this;
		}

		public ApiCallInfo.Builder<I, O> mediaType(MediaType mediaType) {
			this.mediaType = mediaType;
			return this;
		}

		public ApiCallInfo.Builder<I, O> req(ContextSupplier<I> req) {
			this.req = req;
			return this;
		}

		public ApiCallInfo.Builder<I, O> res(ContextConsumer<O> res) {
			this.res = res;
			return this;
		}

		public ApiCallInfo.Builder<I, O> resType(Class<O> resType) {
			this.resType = resType;
			return this;
		}

		public ApiCallInfo.Builder<I, O> resTypeRef(TypeR<O> resType) {
			this.resType = resType.getType();
			return this;
		}

		public ApiCallInfo.Builder<I, O> error(ContextConsumer<RestClientException> error) {
			this.error = error;
			return this;
		}

		public ApiCallInfo.Builder<I, O> copy() {
			return new Builder<>(this);
		}

		public ApiCallInfo<I, O> build() {
			return new ApiCallInfo<>(this);
		}
	}

}
