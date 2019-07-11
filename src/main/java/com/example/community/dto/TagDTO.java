package com.example.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zyh
 * @version 1.0
 * @date 2019/7/10 19:39
 */
@Data
public class TagDTO {
    private String category;
    private List<String> tags;

}
