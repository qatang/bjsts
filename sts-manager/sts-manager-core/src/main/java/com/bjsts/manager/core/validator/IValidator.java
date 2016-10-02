package com.bjsts.manager.core.validator;

import com.bjsts.manager.core.exception.ValidateFailedException;

/**
 * @author jinsheng
 * @since 2016-05-13 13:48
 */
public interface IValidator<T> {

    boolean validate(T t) throws ValidateFailedException;
}
