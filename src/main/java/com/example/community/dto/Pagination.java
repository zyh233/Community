package com.example.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Pagination {

    private boolean firstPage;
    private boolean previous;
    private Integer page;
    private boolean next;
    private boolean lastPage;
    private Integer totalPage;
    private List<QuestionDTO> questions;
    private List<Integer> pages = new ArrayList<>();

    public void set(Integer page, Integer size, int totalPage) {

        this.page = page;
        this.totalPage = totalPage;
        //前一页
        if (page > 1) {
            previous = true;
        } else {
            previous = false;
        }
        //后一页

        if (page < totalPage) {
            next = true;
        } else {
            next = false;
        }

        //首页
        if (page - 2 > 1) {
            firstPage = true;
        } else {
            firstPage = false;
        }
        //尾页
        if (page + 2 < totalPage) {
            lastPage = true;
        } else {
            lastPage = false;
        }

        pages.add(page);
        for (int i = 1; i <= 2; i++) {
            if (page - i >= 1)
                pages.add(0,page-i);
            if (page + i <= totalPage)
                pages.add(page+i);
        }
    }
}
