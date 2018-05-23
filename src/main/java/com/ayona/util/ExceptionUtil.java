package com.ayona.util;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

import java.util.Objects;
import java.util.Optional;

public abstract class ExceptionUtil {

	public static Optional<String> getResponseBodyAsString(RestClientException rce) {
		if (rce.getClass().isAssignableFrom(RestClientResponseException.class)) {
			return Optional.ofNullable(((RestClientResponseException) rce).getResponseBodyAsString());
		}
		return Optional.empty();
	}

	public static Optional<byte[]> getResponseBodyAsByteArray(RestClientException rce) {
		if (rce.getClass().isAssignableFrom(RestClientResponseException.class)) {
			return Optional.ofNullable(((RestClientResponseException) rce).getResponseBodyAsByteArray());
		}
		return Optional.empty();
	}

	public static <T extends Throwable> boolean existTarget(Throwable throwable, Class<T> target) {
		while (throwable != null) {
			if (Objects.equals(throwable.getClass(), target)) {
				return true;
			}
			throwable = throwable.getCause();
		}
		return false;
	}

}
