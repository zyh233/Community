package com.example.community.dto;

import com.example.community.exception.CustomErrorCode;
import com.example.community.exception.CustomException;
import lombok.Data;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zyh
 * @version 1.0
 * @date 2019/7/7 22:33
 */
@Data
public class ResultDTO {

    private Integer code;
    private String message;

    public static ResultDTO error(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO error(CustomErrorCode userNotFind) {

        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(userNotFind.getCode());
        resultDTO.setMessage(userNotFind.getMessage());
        return resultDTO;
    }

    public static ResultDTO success() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static ResultDTO error(CustomException e) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(e.getCode());
        resultDTO.setMessage(e.getMessage());
        return resultDTO;
    }
}
