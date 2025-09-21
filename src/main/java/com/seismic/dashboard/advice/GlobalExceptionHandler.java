package com.seismic.dashboard.advice;

import com.seismic.dashboard.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Server global exception handler
 * Avoid front-end crashes caused by unhandled exceptions
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * In practise, should handle the Exception with specific type, like:
     * MethodArgumentNotValidException，DataAccessException .. etc
     * @param e Unexpected Exception
     * @return General tips
     */
    @ExceptionHandler(Exception.class)
    @Order() // Default Ordered.LOWEST_PRECEDENCE
    public Result handlerException(Exception e) {
        log.error("e.getMessage = {}",  e.getMessage(), e);
        return Result.wrap(false, "Service exception, please contact administrator!");
    }
}
