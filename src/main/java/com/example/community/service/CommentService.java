package com.example.community.service;

import com.example.community.enums.CommentType;
import com.example.community.exception.CustomErrorCode;
import com.example.community.exception.CustomException;
import com.example.community.mapper.CommentMapper;
import com.example.community.mapper.QuestionExtMapper;
import com.example.community.mapper.QuestionMapper;
import com.example.community.model.Comment;
import com.example.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
