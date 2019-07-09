package com.example.community.dto;

import com.example.community.model.User;
import lombok.Data;

/**
 * @author zyh
 * @version 1.0
 * @date 2019/7/8 14:35
 */
@Data
public class CommentReturnDTO {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private String content;
    private User user;
    private Integer commentCount;
}
