package com.example.community.advice;

import com.alibaba.fastjson.JSON;
import com.example.community.dto.ResultDTO;
import com.example.community.exception.CustomErrorCode;
import com.example.community.exception.CustomException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView hadlerException(Exception e, HttpServletRequest request, Model model, HttpServletResponse response) {

        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO;
            if (e instanceof CustomException)
                resultDTO = ResultDTO.error((CustomException)e);
            else resultDTO = ResultDTO.error(CustomErrorCode.SERVER_ERROR);

            try {
                response.setContentType(contentType);
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return null;
        } else {
            if (e instanceof CustomException) {
                model.addAttribute("errorMessage", e.getMessage());
            } else {
                model.addAttribute("errorMessage", "哈哈哈哈...，出错了！");
            }
            return new ModelAndView("error");
        }
    }
}
