package com.ayona;

import com.ayona.command.*;
import com.ayona.context.Context;
import com.ayona.context.ContextSupport;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class CallStream {

	private List<Executable> commands = new LinkedList<>();
	private List<Executable> exhaustedCommands = new LinkedList<>();

	private Caller caller = new SpringRestCaller();
	private ExecutorService executor = Executors.newCachedThreadPool();
	private Context context;

	private CallStream() {
	}

	public static CallStream create() {
		return create(new ContextSupport());
	}

	public static CallStream create(Context context) {
		CallStream callStream = new CallStream();
		callStream.context = context;
		return callStream;
	}

	@SuppressWarnings("unchecked")
	public CallStream add(Supplier<CallInfo> callInfo) {
		this.commands.add(new ExecutionCommand<>(caller, context, callInfo.get()));
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
		this.commands.add(new AsyncExecutionCommand<>(caller, context, callInfo.get()));
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
		executor.shutdown();
	}

	private void printResult() {
		List variable = this.context.getVariable(RecorderCaller.RECORD_KEY_NAME, List.class);
		for (Object o : variable) {
			System.out.println(o);
		}
		System.out.println(variable.size());
	}

}