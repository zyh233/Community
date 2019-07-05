package com.example.community.controller;

import com.example.community.exception.UserException;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    QuestionService service;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description, @RequestParam("tag") String tag,
                            HttpServletRequest request, Model model) {
        try {
            service.createQuestion(title, description, tag, request, model);
        } catch (UserException e) {
            e.printStackTrace();
            return "publish";
        }
        return "redirect:/index";
    }
}
