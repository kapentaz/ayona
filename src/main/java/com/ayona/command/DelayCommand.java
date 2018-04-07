package com.ayona.command;

/**
 * 실행 시간을 지연 시키기 위한 커맨드
 */
public class DelayCommand implements Executable {

	private int millis;

	public DelayCommand(int millis) {
		this.millis = millis;
	}

	@Override
	public void execute() {
		try {
			Thread.sleep(this.millis);
		} catch (InterruptedException e) {
		}
	}

}
