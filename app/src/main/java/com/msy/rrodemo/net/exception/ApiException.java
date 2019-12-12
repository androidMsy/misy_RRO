package com.msy.rrodemo.net.exception;

public class ApiException extends RuntimeException {
    private int code;

    public ApiException(String message) {
        super(new Throwable(message));
    }

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
