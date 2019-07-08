package com.example.community.service;

import com.example.community.dto.Pagination;
import com.example.community.dto.QuestionDTO;
import com.example.community.exception.CustomErrorCode;
import com.example.community.exception.CustomException;
import com.example.community.exception.UserException;
import com.example.community.mapper.QuestionExtMapper;
import com.example.community.mapper.QuestionMapper;
import com.example.community.mapper.UserMapper;
import com.example.community.model.Question;
import com.example.community.model.QuestionExample;
import com.example.community.model.User;
import org.apache.ibatis.session.RowBounds;
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
    @Autowired
    private QuestionExtMapper questionExtMapper;

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
        questionMapper.insert(question);
    }

    public Pagination getQuestions(Integer page, Integer size) {

        int totalQuestion = (int) questionMapper.countByExample(new QuestionExample());

        int totalPage = totalQuestion / size;
        if(totalQuestion % size != 0)
            totalPage++;
        if(page < 1) page = 1;
        if(page > totalPage) page = totalPage;
        Integer offset = size * (page - 1);

        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset,size));
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
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

    public QuestionDTO getQuestionById(Integer id) {

        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomException(CustomErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO dto = new QuestionDTO();
        BeanUtils.copyProperties(question, dto);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        dto.setUser(user);
        return dto;
    }

    public void createOrUpdate(Question question, HttpServletRequest request) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            User user = (User) request.getSession().getAttribute("user");
            question.setCreator(user.getId());
            questionMapper.insert(question);
        } else {
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            question.setId(null);
            int i = questionMapper.updateByExampleSelective(question, questionExample);
            if (i == 0) {
                throw new CustomException(CustomErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incrementView(Integer id) {
        //Question question = questionMapper.selectByPrimaryKey(id);
        Question question = new Question();
//        questionUpdate.setViewCount(question.getViewCount() + 1);
//        QuestionExample example = new QuestionExample();
//        example.createCriteria().andIdEqualTo(id);
        question.setId(id);
        question.setViewCount(1);
//        questionMapper.updateByExampleSelective(questionUpdate, example);
        questionExtMapper.incrementView(question);
    }
}
