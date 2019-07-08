package com.example.community.exception;

public enum CustomErrorCode {
    QUESTION_NOT_FOUND(2000,"问题不见了...."),
    USER_NOT_FIND(2001,"用户未登录，不能进行评论"),
    TARGET_NOT_FOUND(2002, "要回复的问题或评论未找到"),
    SERVER_ERROR(2003, "服务器正忙..."),
    COMMENT_TYPE_NOT_EXIST(2004, "评论类型不存在"),
    COMMENT_NOT_FOUND(2005, "评论不存在");
    private Integer code;
    private String message;

    CustomErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
