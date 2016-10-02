package com.bjsts.manager.handler;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jinsheng
 * @since 2016-04-28 09:49
 */
@ControllerAdvice
public class WebExceptionHandler {
    
    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(UnauthorizedException.class)
    public RedirectView handleUnauthorizedException(HttpServletRequest request) {
        RedirectView redirectView = new RedirectView("/unauthorized");
        FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        if (outputFlashMap != null){
            outputFlashMap.put("url", request.getRequestURL());
        }
        return redirectView;
    }

    @ExceptionHandler(Exception.class)
    public RedirectView handleException(HttpStatus httpStatus, HttpServletRequest request, Exception exception) {
        logger.error(exception.getMessage(), exception);

        switch (httpStatus) {
            case NOT_FOUND:
                return new RedirectView("/404");
            case INTERNAL_SERVER_ERROR:
                return new RedirectView("/500");
            default:
                break;
        }

        RedirectView redirectView = new RedirectView("/error");
        FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        if (outputFlashMap != null){
            outputFlashMap.put("errorMessage", exception.getMessage());
            outputFlashMap.put("ex", exception);
            outputFlashMap.put("url", request.getRequestURL());
        }
        return redirectView;
    }
}
