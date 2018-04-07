package com.ayona.command;

import com.ayona.CallInfo;
import com.ayona.Caller;
import com.ayona.context.Context;

/**
 * {@link CallInfo} 처리를 위한 커맨드
 */
public class ExecutionCommand<T extends CallInfo> implements Executable {

	private final T[] callInfos;
	private final Caller<T> caller;
	private final Context context;

	public ExecutionCommand(Caller<T> caller, Context context, T... callInfos) {
		this.caller = caller;
		this.context = context;
		this.callInfos = callInfos;
	}

	@Override
	public void execute() {
		for (T callInfo : callInfos) {
			caller.execute(callInfo, context);
		}
	}

}
