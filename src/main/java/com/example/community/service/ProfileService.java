package com.example.community.service;

import com.example.community.dto.Pagination;
import com.example.community.dto.QuestionDTO;
import com.example.community.mapper.QuestionMapper;
import com.example.community.model.Question;
import com.example.community.model.QuestionExample;
import com.example.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {

    @Autowired
    QuestionMapper mapper;

    public Pagination getQuestionByCreator(Integer id, Integer page, Integer size, User user) {
//        int totalQuestion = mapper.countById(id);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(id);
        int totalQuestion = (int) mapper.countByExample(questionExample);
        int totalPage = totalQuestion / size;
        if(totalQuestion % size != 0)
            totalPage++;
        if(page < 1) page = 1;
        if(page > totalPage) page = totalPage;
        Integer offset = size * (page - 1);

        List<Question> questions = mapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));
//        List<Question>  questions = mapper.getQuestionByCreator(id, offset, size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questions) {
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
}
