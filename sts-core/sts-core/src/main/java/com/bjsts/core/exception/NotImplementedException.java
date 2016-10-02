package com.bjsts.core.exception;

/**
 * 功能未实现异常
 * Created by sunshow on 5/7/15.
 */
public class NotImplementedException extends RuntimeException {

    private static final long serialVersionUID = -4584128352014050143L;

    public NotImplementedException() {
        this("功能未实现异常");
    }

    public NotImplementedException(String message) {
        super(message);
    }

    public NotImplementedException(Throwable cause) {
        super(cause);
    }

    public NotImplementedException(String message, Throwable cause) {
        super(message, cause);
    }
}
