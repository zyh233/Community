package com.example.community.controller;

import com.example.community.dto.NotificationDTO;
import com.example.community.mapper.NotificationMapper;
import com.example.community.model.User;
import com.example.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zyh
 * @version 1.0
 * @date 2019/7/11 21:21
 */
@Controller
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String notification(@PathVariable("id") Long id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/index";
        }
        NotificationDTO notificationDTO = notificationService.read(id, user);
        return "redirect:/question/" + notificationDTO.getOuterid();
    }
}
