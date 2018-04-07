package com.ayona.report;

import com.ayona.CallInfo;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * {@link CallInfo} 실행결과 기록
 */
@Data
public class CallInfoRecord {
	private Object id;
	private long executedTime = 0;
	private LocalDateTime endDateTime;

	private String input;
	private Object result;

	private Throwable throwable;

	public CallInfoRecord(Object id) {
		this.id = id;
	}

}
