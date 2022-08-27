package com.khopan.file;

public interface ExceptionHandler {
	public static final ExceptionHandler DEFAULT_HANDLER = new DefaultExceptionHandler();

	public void runtimeException(RuntimeException Exception);
	public void exception(Exception Exception);
	public void error(Error Error);
	public void throwable(Throwable Throwable);
	public void terminate(int ExitCode);
	public void terminate();

	public class DefaultExceptionHandler implements ExceptionHandler {
		@Override
		public void runtimeException(RuntimeException Exception) {
			Exception.printStackTrace();
		}

		@Override
		public void exception(Exception Exception) {
			Exception.printStackTrace();
		}

		@Override
		public void error(Error Error) {
			Error.printStackTrace();
		}

		@Override
		public void throwable(Throwable Throwable) {
			Throwable.printStackTrace();
		}

		@Override
		public void terminate() {
			this.terminate(0);
		}

		@Override
		public void terminate(int ExitCode) {
			System.exit(ExitCode);
		}
	}
}
