package com.example.community.controller;

import com.example.community.dto.CommentReturnDTO;
import com.example.community.dto.QuestionDTO;
import com.example.community.enums.CommentType;
import com.example.community.model.Question;
import com.example.community.service.CommentService;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id, Model model) {
        QuestionDTO questionDTO = questionService.getQuestionById(id);
        List<QuestionDTO> questions = questionService.selectRelated(questionDTO);
        model.addAttribute("question", questionDTO);
        model.addAttribute("relatedQuestion", questions);
        questionService.incrementView(id);
        List<CommentReturnDTO> comments = commentService.listByTargetId(id, CommentType.QUESTION);
        model.addAttribute("comments", comments);
        return "question";
    }
}
