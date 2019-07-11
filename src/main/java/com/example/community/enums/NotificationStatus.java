package com.example.community.enums;

/**
 * @author zyh
 * @version 1.0
 * @date 2019/7/10 22:04
 */
public enum NotificationStatus {
    UNREAD(0),
    READ(1);
    private Integer status;

    NotificationStatus(int status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
