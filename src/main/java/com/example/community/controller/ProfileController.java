package com.example.community.controller;

import com.example.community.dto.Pagination;
import com.example.community.model.Notification;
import com.example.community.model.User;
import com.example.community.service.NotificationService;
import com.example.community.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    ProfileService service;

    @Autowired
    NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String select(@PathVariable("action") String action, Model model,
                         @RequestParam(value = "page", defaultValue = "1") Integer page,
                         @RequestParam(value = "size", defaultValue = "7") Integer size,
                         HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if ("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的问题");
            Pagination pagination = service.getQuestionByCreator(user.getId(), page, size, user);
            model.addAttribute("pagination", pagination);

            model.addAttribute("questionCount", pagination.getData().size());
        } else if ("replies".equals(action)) {
            Pagination pagination = notificationService.list(user.getId(), page, size);
            model.addAttribute("pagination", pagination);
            long count = notificationService.unReadCount(user.getId());
            model.addAttribute("unReadCount", count);
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }
        return "profile";
    }
}
