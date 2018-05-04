package com.ayona;

import com.ayona.context.Context;
import com.ayona.context.ContextConsumer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * Spring {@link RestTemplate}를 이용하는 {@link Caller}
 */
public class SpringRestCaller extends RecorderCaller<ApiCallInfo<?, ?>> {

	private final RestTemplate restTemplate;

	public SpringRestCaller() {
		this(new RestTemplate());
	}

	public SpringRestCaller(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object doExecute(ApiCallInfo<?, ?> apiCallInfo, Context context) {
		String uri = apiCallInfo.getUri().get(context);
		HttpMethod method = apiCallInfo.getMethod();
		MediaType mediaType = apiCallInfo.getMediaType();
		Object req = apiCallInfo.getReq().get(context);
		ContextConsumer result = apiCallInfo.getRes();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(mediaType);
		HttpEntity httpEntity = new HttpEntity(req == null ? null : req, httpHeaders);

		Object forObject = restTemplate.exchange(uri, method, httpEntity, req.getClass()).getBody();

		if (result != null) {
			result.accept(context, forObject);
		}
		return forObject;
	}

}
