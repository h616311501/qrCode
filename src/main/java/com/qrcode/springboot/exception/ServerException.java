package com.qrcode.springboot.exception;

import lombok.Getter;

@Getter
public class ServerException extends RuntimeException {
    private String code;
    public ServerException(String code, String msg){
        super(msg);
        this.code = code;
    }
}
