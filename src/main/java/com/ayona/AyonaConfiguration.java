package com.ayona;

import com.ayona.context.Context;
import com.ayona.context.ContextSupport;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AyonaConfiguration {

	private static final AyonaConfiguration DEFAULT = create();

	private final Caller caller;
	private final ExecutorService executor;
	private final Context context;

	private AyonaConfiguration(Builder builder) {
		this.caller = (builder.caller == null) ? new SpringRestCaller() : builder.caller;
		this.executor = (builder.executor == null) ? Executors.newCachedThreadPool() : builder.executor;
		this.context = (builder.context == null) ? new ContextSupport() : builder.context;
	}

	public static AyonaConfiguration getDefault() {
		return DEFAULT;
	}

	private static AyonaConfiguration create() {
		return create(new ContextSupport());
	}

	public static AyonaConfiguration create(Context context) {
		return builder()
				.caller(new SpringRestCaller())
				.executor(Executors.newCachedThreadPool())
				.context(context)
				.build();
	}

	public static Builder builder() {
		return new Builder();
	}

	public Caller getCaller() {
		return caller;
	}

	public ExecutorService getExecutor() {
		return executor;
	}

	public Context getContext() {
		return context;
	}

	public static class Builder {
		private Caller caller;
		private ExecutorService executor;
		private Context context;

		public Builder caller(Caller caller) {
			this.caller = caller;
			return this;
		}

		public Builder executor(ExecutorService executor) {
			this.executor = executor;
			return this;
		}

		public Builder context(Context context) {
			this.context = context;
			return this;
		}

		public AyonaConfiguration build() {
			return new AyonaConfiguration(this);
		}
	}

}
