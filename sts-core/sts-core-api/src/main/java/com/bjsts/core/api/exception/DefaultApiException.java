package com.bjsts.core.api.exception;

/**
 * 统一API异常, 其他自定义异常均继承
 * Created by sunshow on 5/7/15.
 */
public class DefaultApiException extends RuntimeException {

    private static final long serialVersionUID = -7681216209917365385L;

    public DefaultApiException() {
        this("Api调用异常");
    }

    public DefaultApiException(String message) {
        super(message);
    }

    public DefaultApiException(Throwable cause) {
        super(cause);
    }

    public DefaultApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
