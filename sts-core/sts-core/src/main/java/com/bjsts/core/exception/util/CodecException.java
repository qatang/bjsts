package com.bjsts.core.exception.util;

/**
 * 各类编码异常
 * Created by sunshow on 5/7/15.
 */
public class CodecException extends RuntimeException {

    private static final long serialVersionUID = -3708410383929118742L;

    public CodecException() {
        this("加密或编码异常");
    }

    public CodecException(String message) {
        super(message);
    }

    public CodecException(Throwable cause) {
        super(cause);
    }

    public CodecException(String message, Throwable cause) {
        super(message, cause);
    }
}
