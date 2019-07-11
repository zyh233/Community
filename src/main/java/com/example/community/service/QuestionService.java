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
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    public Pagination getQuestions(Integer page, Integer size) {

        int totalQuestion = (int) questionMapper.countByExample(new QuestionExample());

        int totalPage = totalQuestion / size;
        if(totalQuestion % size != 0)
            totalPage++;
        if(page < 1) page = 1;
        if(page > totalPage) page = totalPage;
        Integer offset = size * (page - 1);

        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset,size));
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        Pagination pagination = new Pagination();
        pagination.setData(questionDTOS);
        pagination.set(page, size, totalPage);
        return pagination;
    }

    /**
     * 根據Id查找問題
     * @param id
     * @return
     */
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

    /**
     * 增加或者更新問題
     * @param question
     * @param request
     */
    public void createOrUpdate(Question question, HttpServletRequest request) {
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            User user = (User) request.getSession().getAttribute("user");
            question.setCreator(user.getId());
            question.setCommentCount(0);
            question.setViewCount(0);
            question.setLikeCount(0);
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

    /**
     * 增加問題瀏覽數
     * @param id
     */
    public void incrementView(Integer id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incrementView(question);
    }

    /**
     * 查找與本問題相關的問題
     * @param questionDTO
     * @return
     */
    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {

        if (questionDTO.getTag() == null) {
            return new ArrayList<>();
        }
        String[] tags = questionDTO.getTag().split(",");
        String regex = String.join("|", tags);
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(regex);
        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> collect = questions.stream().map(q -> {
            QuestionDTO dto = new QuestionDTO();
            BeanUtils.copyProperties(q, dto);
            return dto;
        }).collect(Collectors.toList());
        return collect;
    }
}
