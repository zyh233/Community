package com.example.community.dto;

import lombok.Data;

/**
 * @author zyh
 * @version 1.0
 * @date 2019/7/10 22:38
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Integer type;
    private Integer outerid;
//    private User notifier;
    private String questionTitle;
    private String notifierName;
}
