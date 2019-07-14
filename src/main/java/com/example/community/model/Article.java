package com.example.community.model;

import io.searchbox.annotations.JestId;
import lombok.Data;

/**
 * @author zyh
 * @version 1.0
 * @date 2019/7/13 11:35
 */
//@Document(indexName = "coder", type = "article")
@Data
public class Article {

    @JestId
    private Integer id;
    private String title;
    private String content;
    private String tag;
    private Integer author;
    private String authorName;
    private Long gmtCreate;
}
