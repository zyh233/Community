package com.example.community.controller;

import com.example.community.dto.ArticleDTO;
import com.example.community.dto.Pagination;
import com.example.community.dto.TagDTO;
import com.example.community.exception.CustomErrorCode;
import com.example.community.exception.CustomException;
import com.example.community.model.Article;
import com.example.community.model.User;
import com.example.community.service.ArticleService;
import com.example.community.util.TagUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zyh
 * @version 1.0
 * @date 2019/7/13 10:18
 */

@Controller
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @GetMapping("/article/page")
    public String articlePage(Model model) {
        List<TagDTO> tags = TagUtil.getTags();
        model.addAttribute("tags", tags);
        return "article";
    }

    @PostMapping("/article")
    public String submitArticle(@RequestParam("title") String title,
                                @RequestParam("content") String content,
                                @RequestParam("tag") String tag,
                                HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException(CustomErrorCode.USER_NOT_FIND);
        }
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setTag(tag);
        article.setAuthor(user.getId());
        article.setAuthorName(user.getName());
        article.setGmtCreate(System.currentTimeMillis());
        int id = articleService.insert(article);

        return "redirect:/article/" + id;
    }

    @GetMapping("/article/{id}")
    public String showArticle(@PathVariable("id") Integer id, Model model) {
        ArticleDTO articleDTO = articleService.getById(id);
        model.addAttribute("article", articleDTO);
        return "showArticle";
    }

    @GetMapping("/article")
    public String list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                       Model model) {

        Pagination<ArticleDTO> pagination = articleService.list(page, 5);
        model.addAttribute("pagination", pagination);
        return "articleList";
    }

    @GetMapping("/article/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        Pagination<ArticleDTO> pagination = articleService.searchByCondition(keyword);
        model.addAttribute("pagination", pagination);
        return "articleList";
    }
}
