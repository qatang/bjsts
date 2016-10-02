package com.bjsts.core.exception.retrymodel;


public class ApiRetryTaskException extends RuntimeException {

	private static final long serialVersionUID = -3034793585657723619L;

	public ApiRetryTaskException(String message) {
		super(message);
	}
	
	public ApiRetryTaskException() {
		this("重试任务执行失败");
	}

	public ApiRetryTaskException(Throwable cause) {
		super(cause);
	}

	public ApiRetryTaskException(String message, Throwable cause) {
		super(message, cause);
	}
}
