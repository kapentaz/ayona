package com.ayona;

import com.ayona.command.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class CallStream {

	private List<Executable> commands = new LinkedList<>();
	private List<Executable> exhaustedCommands = new LinkedList<>();

	private AyonaConfiguration config;

	private CallStream() {
	}

	public static CallStream create() {
		return create(AyonaConfiguration.getDefault());
	}

	public static CallStream create(AyonaConfiguration configuration) {
		CallStream callStream = new CallStream();
		callStream.config = configuration;
		return callStream;
	}

	@SuppressWarnings("unchecked")
	public CallStream add(Supplier<CallInfo> callInfo) {
		this.commands.add(new ExecutionCommand<>(config.getCaller(), config.getContext(), callInfo.get()));
		return this;
	}

	@SuppressWarnings("unchecked")
	public CallStream addAsync(Supplier<CallInfo> callInfo) {
		//
		if (this.commands.size() > 0) {
			Executable executable = this.commands.get(this.commands.size() - 1);
			Class<? extends Executable> type = executable.getClass();
			if (AsyncExecutionCommand.class == type) {
				((AsyncExecutionCommand) executable).add(callInfo.get());
				return this;
			}
		}
		this.commands.add(new AsyncExecutionCommand<>(config.getCaller(), config.getContext(), callInfo.get()));
		return this;
	}

	/**
	 * 이미 실행한 작업을 제외한 숫자를 입력해야 한다.
	 */
	public CallStream loop(int loop) {
		this.commands.add(new LoopCommand(exhaustedCommands, loop));
		return this;
	}

	public CallStream delay(int mills) {
		this.commands.add(new DelayCommand(mills));
		return this;
	}

	@SuppressWarnings("unchecked")
	public void run() {
		for (Executable command : commands) {
			try {
				command.execute();
				exhaustedCommands.add(command);
			} catch (Throwable e) {
				printResult();
				throw e;
			}
		}
		printResult();
		config.getExecutor().shutdown();
	}

	@SuppressWarnings("unchecked")
	private void printResult() {
		List variable = config.getContext().get(RecorderCaller.RECORD_KEY_NAME, List.class);
		Optional.ofNullable(variable)
				.orElse(Collections.emptyList())
				.forEach(System.out::println);
	}

}
