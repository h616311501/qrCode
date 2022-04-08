package com.qrcode.springboot.exception;

import com.qrcode.springboot.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServerException.class)
    @ResponseBody
    public Result handle(ServerException se)
    {
        return Result.error(se.getCode(),se.getMessage());
    }
}
