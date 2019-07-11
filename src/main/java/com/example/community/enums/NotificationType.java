package com.example.community.enums;

/**
 * @author zyh
 * @version 1.0
 * @date 2019/7/10 22:04
 */
public enum NotificationType {
    REPLY_QUESTION(1, "回復了問題"),
    REPLY_COMMENT(2,"回復了評論");
    private Integer type;
    private String name;

    NotificationType(int status, String name) {
        this.type = status;
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
