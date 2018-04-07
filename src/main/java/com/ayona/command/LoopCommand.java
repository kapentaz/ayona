package com.ayona.command;

import java.util.List;

/**
 * 반복 실행을 위한 커맨드
 */
public class LoopCommand implements Executable {

	private final List<Executable> exhaustedCommands;
	private int loop;

	public LoopCommand(List<Executable> exhaustedCommands, int loop) {
		this.exhaustedCommands = exhaustedCommands;
		this.loop = loop;
	}

	@Override
	public void execute() {
		for (int i = loop; i > 0; i--) {
			exhaustedCommands.forEach(Executable::execute);
		}
	}

}
