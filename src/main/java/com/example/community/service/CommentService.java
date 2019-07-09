package com.example.community.service;

import com.example.community.dto.CommentReturnDTO;
import com.example.community.enums.CommentType;
import com.example.community.exception.CustomErrorCode;
import com.example.community.exception.CustomException;
import com.example.community.mapper.*;
import com.example.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zyh
 * @version 1.0
 * @date 2019/7/7 22:49
 */
@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CommentExtMapper commentExtMapper;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomException(CustomErrorCode.TARGET_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentType.isExist(comment.getType())) {
            throw new CustomException(CustomErrorCode.COMMENT_TYPE_NOT_EXIST);
        }

        if (comment.getType() == CommentType.COMMENT.getType()) {
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw  new  CustomException(CustomErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
            dbComment.setCommentCount(1);
            commentExtMapper.incrementComment(dbComment);
        } else {
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomException(CustomErrorCode.QUESTION_NOT_FOUND);
            }
            //回复问题
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incrementComment(question);
        }

    }


    /**
     * 根据问题id查评论
     * @param id 问题的id
     * @return 编号为id的问题的评论
     */
    public List<CommentReturnDTO> listByTargetId(Integer id, CommentType type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if (comments.size() == 0) {
            return null;
        }
        Set<Integer> creators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> list = new ArrayList<>(creators);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(list);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        List<CommentReturnDTO> commentDTOS = comments.stream().map(comment -> {
            CommentReturnDTO dto = new CommentReturnDTO();
            BeanUtils.copyProperties(comment, dto);
            dto.setUser(userMap.get(dto.getCommentator()));
            return dto;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
