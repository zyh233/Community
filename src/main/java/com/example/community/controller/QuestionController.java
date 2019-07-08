package com.example.community.controller;

import com.example.community.dto.QuestionDTO;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id, Model model) {
        QuestionDTO questionDTO = questionService.getQuestionById(id);
        questionService.incrementView(id);
        model.addAttribute("question", questionDTO);
        return "question";
    }
}
