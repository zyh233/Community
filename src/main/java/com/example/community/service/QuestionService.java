package com.example.community.service;

import com.example.community.dto.Pagination;
import com.example.community.dto.QuestionDTO;
import com.example.community.exception.UserException;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

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
        questionMapper.create(question);
    }

    public Pagination getQuestions(Integer page, Integer size) {

        int totalQuestion = questionMapper.count();
        int totalPage = totalQuestion / size;
        if(totalQuestion % size != 0)
            totalPage++;
        if(page < 1) page = 1;
        if(page > totalPage) page = totalPage;
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.getQuestionByPage(offset, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.getUserById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        Pagination pagination = new Pagination();
        pagination.setQuestions(questionDTOS);
        pagination.set(page, size, totalPage);
        return pagination;
    }

    public QuestionDTO getQuestionById(Integer id, User user) {

        Question question = questionMapper.getById(id);
        QuestionDTO dto = new QuestionDTO();
        BeanUtils.copyProperties(question, dto);
        dto.setUser(user);
        return dto;
    }
}
