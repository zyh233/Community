package com.example.community.controller;

import com.example.community.dto.CommentDTO;
import com.example.community.dto.CommentReturnDTO;
import com.example.community.dto.ResultDTO;
import com.example.community.enums.CommentType;
import com.example.community.exception.CustomErrorCode;
import com.example.community.model.Comment;
import com.example.community.model.User;
import com.example.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.error(CustomErrorCode.USER_NOT_FIND);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        commentService.insert(comment);
        return ResultDTO.success();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<CommentReturnDTO> subComments(@PathVariable("id") Integer id) {
        List<CommentReturnDTO> commentsByQID = commentService.listByTargetId(id, CommentType.COMMENT);
        //System.out.println(commentsByQID.size());
        //model.addAttribute("subComments", commentsByQID);
        return ResultDTO.success(commentsByQID);
    }
}
