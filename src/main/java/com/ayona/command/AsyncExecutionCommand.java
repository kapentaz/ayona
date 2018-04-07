package com.ayona.command;

import com.ayona.CallInfo;
import com.ayona.Caller;
import com.ayona.context.Context;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * 비동기 처리를 위한 커멘드. 주어진 {@link CallInfo}가 모두 처리 될 때까지 대기한다.
 */
public class AsyncExecutionCommand<T extends CallInfo> implements Executable {

	private final Caller<T> caller;
	private final Context context;
	private final List<T> callInfos = new LinkedList<>();

	public AsyncExecutionCommand(Caller<T> caller, Context context, T... callInfos) {
		this.caller = caller;
		this.context = context;
		this.callInfos.addAll(Arrays.asList(callInfos));
	}

	@Override
	public void execute() {
		List<CompletableFuture<Void>> futures = callInfos.stream()
				.map(ci -> CompletableFuture.runAsync(() -> caller.execute(ci, context)))
				.collect(Collectors.toList());

		futures.forEach(CompletableFuture::join);
	}

	public void add(T... callInfos) {
		this.callInfos.addAll(Arrays.asList(callInfos));
	}

}
