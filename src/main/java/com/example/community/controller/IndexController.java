package com.example.community.controller;

import com.example.community.dto.Pagination;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/index")
    public String index(Model model, @RequestParam(value = "page", defaultValue = "1") Integer page) {

        Pagination questions = questionService.getQuestions(page,5);

        model.addAttribute("pagination", questions);
        return "index";
    }
}
