package com.ayona.context;

import com.ayona.TypeR;

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

	String CONTEXT_DATA_DELIMITER = "$.";

	void set(String name, Object value);

	Object get(String name);

	<T> T get(String name, Class<T> type);

	<T> T get(String name, TypeR<T> type);

	Object remove(String name);

	String[] getNames();

	boolean has(String name);

	/**
	 * values 의 각 객체를 문자열(toString())로 연결한다.
	 *
	 * @param values 대상 객체 배열
	 * @return 연결된 문자열
	 */
	default String concat(Object... values) {
		return format(Stream.of(values)
				.map(v -> "{}")
				.collect(Collectors.joining("")), values);
	}

	/**
	 * 내부적으로 replaceBraces 메소드를 호출한다.
	 * 다른점은 value 가 String 타입이며 prefix 가 CONTEXT_DATA_DELIMITER 형태일 경우 {@link Context} 에 저장된 객체를 반환한다.
	 *
	 * @param format 대상 문자
	 * @param values 치환할 객체 배열
	 * @return 치환된 대상  문자
	 */
	default String format(String format, Object... values) {
		List<Object> temp = new ArrayList<>();
		for (Object obj : values) {
			if (obj.getClass() == String.class) {
				String s = (String) obj;
				if (s.startsWith(CONTEXT_DATA_DELIMITER)) {
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

	/**
	 * {..} 문자를 objects 의 순서대로 치환한다.
	 *
	 * @param value   대상 문자
	 * @param objects 치환할 객체 배열
	 * @return 치환된 대상  문자
	 */
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
