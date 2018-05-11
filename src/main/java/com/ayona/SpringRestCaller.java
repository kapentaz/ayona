package com.ayona;

import com.ayona.context.Context;
import com.ayona.context.ContextConsumer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * Spring {@link RestTemplate}를 이용하는 {@link Caller}
 */
public class SpringRestCaller extends RecorderCaller<ApiCallInfo<Object, Object>> {

	private final RestTemplate restTemplate;

	public SpringRestCaller() {
		this(new RestTemplate());
	}

	public SpringRestCaller(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object doExecute(ApiCallInfo<Object, Object> apiCallInfo, Context context) {
		String uri = apiCallInfo.getUri().get(context);
		HttpMethod method = apiCallInfo.getMethod();
		MediaType mediaType = apiCallInfo.getMediaType();
		Object req = apiCallInfo.getReq().map(r -> r.get(context)).orElse(null);
		Optional<ContextConsumer<Object>> res = apiCallInfo.getRes();
		Class<?> resType = apiCallInfo.getResType();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(mediaType);
		HttpEntity httpEntity = new HttpEntity(req == null ? null : req, httpHeaders);

		try {
			Object forObject = restTemplate.exchange(uri, method, httpEntity, resType).getBody();
			res.ifPresent(r -> r.accept(context, forObject));
			return forObject;
		} catch (RestClientException e) {
			apiCallInfo.getError().accept(context, e);
		}
		return null;
	}

}
