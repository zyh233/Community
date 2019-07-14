package com.example.community.exception;

public enum CustomErrorCode {
    QUESTION_NOT_FOUND(2000,"问题不见了...."),
    USER_NOT_FIND(2001,"用户未登录，不能进行评论"),
    TARGET_NOT_FOUND(2002, "要回复的问题或评论未找到"),
    SERVER_ERROR(2003, "服务器正忙..."),
    COMMENT_TYPE_NOT_EXIST(2004, "评论类型不存在"),
    COMMENT_NOT_FOUND(2005, "评论不存在"),
    READ_NOTIFICATION_FAIL(2006, "通知不是您的"),
    NOTIFICATION_NOT_EXIST(2007,"通知不存在"),
    ARTICLE_ADD_FAIL(2008, "亲，您的文章迷失在网络中了"),
    ARTICLE_NOT_FOUND(2009, "文章不小心丢失了..."),
    UPLOAD_IMAGE_FAILED(2010, "图片上传失败...");
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
