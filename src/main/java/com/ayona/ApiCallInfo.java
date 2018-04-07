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
@Setter
@Getter
public class ApiCallInfo<I, O> implements CallInfo {

	private ContextSupplier<String> id;
	private ContextSupplier<String> uri;
	private HttpMethod method;
	private MediaType mediaType;
	private ContextSupplier<I> req;
	private ParameterizedTypeReference<O> resTypeRef;
	private ContextConsumer<O> res;

	@Override
	public String toString(Context context) {
		return "[" + mediaType + ", " + method + "] " + uri.get(context);
	}

}
