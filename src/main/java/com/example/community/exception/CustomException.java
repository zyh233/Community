package com.example.community.exception;

public class CustomException extends RuntimeException{

    private Integer code;
    private String message;
    public CustomException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public CustomException(CustomErrorCode customErrorCode) {
        this.code = customErrorCode.getCode();
        this.message = customErrorCode.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
