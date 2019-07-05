package com.example.community.service;

import com.example.community.exception.UserException;
import com.example.community.mapper.QuestionMapper;
import com.example.community.model.Question;
import com.example.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper mapper;
    public void createQuestion(String title, String description, String tag, HttpServletRequest request, Model model) throws UserException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            throw new UserException("用户未登录");
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        long millis = System.currentTimeMillis();
        question.setGmtCreate(millis);
        question.setGmtModified(millis);
        mapper.create(question);
    }
}
