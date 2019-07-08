package com.example.community.enums;

/**
 * @author zyh
 * @version 1.0
 * @date 2019/7/7 22:47
 */
public enum CommentType {
    QUESTION(1),
    COMMENT(2);

    private Integer type;

    CommentType(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {

        for (CommentType value : CommentType.values()) {
            if (value.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }
}
