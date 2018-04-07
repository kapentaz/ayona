package com.ayona;

import com.ayona.context.Context;
import com.ayona.report.CallInfoRecord;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 실행결과를 기록하는 역활을 한다.
 */
@Slf4j
public abstract class RecorderCaller<T extends CallInfo> implements Caller<T> {

	public static final String RECORD_KEY_NAME = "__CALL_INFO_RECORD__";

	@SuppressWarnings("unchecked")
	@Override
	public void execute(T t, Context context) {
		Object id = t.getId().get(context);
		CallInfoRecord callInfoRecord = new CallInfoRecord(id);

		long start = System.currentTimeMillis();

		try {
			callInfoRecord.setInput(t.toString(context));
			Object result = doExecute(t, context);
			callInfoRecord.setResult(result);
		} catch (Throwable e) {
			callInfoRecord.setThrowable(e);
			throw e;
		}

		long end = System.currentTimeMillis();
		long executedTime = end - start;
		log.debug("[{}] executed time: {}ms", id, executedTime);

		callInfoRecord.setExecutedTime(executedTime);
		callInfoRecord.setEndDateTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(end), ZoneId.systemDefault()));

		if (!context.hasVariable(RECORD_KEY_NAME)) {
			context.setVariable(RECORD_KEY_NAME, new CopyOnWriteArrayList<>());
		}
		List callInfoRecords = context.getVariable(RECORD_KEY_NAME, List.class);
		callInfoRecords.add(callInfoRecord);
	}

	public abstract Object doExecute(T t, Context context);

}
