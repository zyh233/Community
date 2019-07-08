package com.example.community.dto;

import lombok.Data;

/**
 * @author zyh
 * @version 1.0
 * @date 2019/7/7 21:45
 */
@Data
public class CommentDTO {

    private Integer parentId;
    private Integer type;
    private String content;


}
