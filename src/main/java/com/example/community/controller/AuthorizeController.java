package com.example.community.controller;

import com.example.community.dto.AccessTokenDTO;
import com.example.community.dto.GithubUser;
import com.example.community.model.User;
import com.example.community.provider.GithubProvider;
import com.example.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    GithubProvider provider;
    @Autowired
    UserService userService;

    @Value("${github.client_id}")
    private String clientId;
    @Value("${github.client_secret}")
    private String clientSecret;
    @Value("${github.redirect_uri}")
    private String redirectUri;


    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code, @RequestParam("state") String state,
                            HttpServletResponse response) {
        AccessTokenDTO dto = new AccessTokenDTO();
        dto.setClient_id(clientId);
        dto.setClient_secret(clientSecret);
        dto.setCode(code);
        dto.setRedirect_uri(redirectUri);
        dto.setState(state);
        String token = provider.getAccessToken(dto);
        GithubUser githubUser = provider.getGithubUser(token);
        if(githubUser != null) {
            User user = new User();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setToken(UUID.randomUUID().toString());
            user.setName(githubUser.getName());
            user.setAvatarUrl(githubUser.getAvatar_url());
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token", user.getToken()));
            return "redirect:/index";
        } else {
            return "redirect:/index";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/index";
    }
}
