package com.example.community.dto;

import lombok.Data;

/**
 * @author zyh
 * @version 1.0
 * @date 2019/7/13 10:38
 */
@Data
public class ArticleDTO {
    private Integer id;
    private String title;
    private String content;
    private String tag;
    private String authorName;
    private Long gmtCreate;
}
