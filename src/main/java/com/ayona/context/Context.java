package com.ayona.context;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Caller 를 통해서 실행된 정보를 저장하거나, 필요한 공유 변수를 정의해서 사용할 수 있다.
 */
public interface Context {

	String CONTEXT_DELIMITER = "$.";

	void set(String name, Object value);

	Object get(String name);

	<T> T get(String name, Class<T> type);

	Object remove(String name);

	String[] getNames();

	boolean has(String name);

	default String concat(Object... value) {
		return format(Stream.of(value)
				.map(v -> "{}")
				.collect(Collectors.joining("")), value);
	}

	default String format(String format, Object... value) {
		List<Object> temp = new ArrayList<>();
		for (Object obj : value) {
			if (obj.getClass() == String.class) {
				String s = (String) obj;
				if (s.startsWith(CONTEXT_DELIMITER)) {
					temp.add(get(s.substring(2)));
				} else {
					temp.add(s);
				}
			} else {
				temp.add(obj);
			}
		}
		return replaceBraces(format, temp.toArray());
	}

	default String replaceBraces(String value, Object... objects) {
		Matcher matcher = Pattern.compile("\\{(.*?)\\}").matcher(value);

		if (objects != null && objects.length > 0) {
			int start = 0;
			int end = objects.length;

			StringBuffer s = new StringBuffer();
			while (matcher.find()) {
				if (start < end) {
					Object obj = objects[start];
					start++;
					if (obj == null) {
						continue;
					}
					matcher.appendReplacement(s, obj.toString());
				}
			}
			return matcher.appendTail(s).toString();
		} else {
			return value;
		}
	}

}
