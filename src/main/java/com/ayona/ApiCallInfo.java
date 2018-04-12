package com.ayona;

import com.ayona.context.Context;
import com.ayona.context.ContextConsumer;
import com.ayona.context.ContextSupplier;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

/**
 * API 호출 정보를 정의한다.
 */
public class ApiCallInfo<I, O> implements CallInfo {

	@Setter @Getter
	private ContextSupplier<String> id;

	@Getter
	private ContextSupplier<String> uri;

	@Getter
	private HttpMethod method;

	@Setter @Getter
	private MediaType mediaType;

	@Setter @Getter
	private ContextSupplier<I> req;

	@Setter @Getter
	private ParameterizedTypeReference<O> resTypeRef;

	@Setter @Getter
	private ContextConsumer<O> res;

	@Override
	public String toString(Context context) {
		return "[" + mediaType + ", " + method + "] " + uri.get(context);
	}

	public void get(ContextSupplier<String> uri) {
		this.uri = uri;
		this.method = HttpMethod.GET;
	}

	public void post(ContextSupplier<String> uri) {
		this.uri = uri;
		this.method = HttpMethod.POST;
	}

	public void put(ContextSupplier<String> uri) {
		this.uri = uri;
		this.method = HttpMethod.PUT;
	}

	public void delete(ContextSupplier<String> uri) {
		this.uri = uri;
		this.method = HttpMethod.DELETE;
	}

	public void head(ContextSupplier<String> uri) {
		this.uri = uri;
		this.method = HttpMethod.HEAD;
	}

}
