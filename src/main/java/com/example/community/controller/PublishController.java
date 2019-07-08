package com.example.community.controller;

import com.example.community.dto.QuestionDTO;
import com.example.community.model.Question;
import com.example.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            @RequestParam("id") Integer id,
                            HttpServletRequest request) {
        Question question = new Question();
        question.setId(id);
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        service.createOrUpdate(question, request);
        return "redirect:/index";
    }

    @GetMapping("/publish/{id}")
    public String editQuestion(@PathVariable("id") Integer id, Model model) {
        QuestionDTO question = service.getQuestionById(id);
        model.addAttribute("editQuestion", question);
        return "publish";
    }
}
