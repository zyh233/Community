package com.example.community.util;

import com.example.community.dto.TagDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zyh
 * @version 1.0
 * @date 2019/7/10 19:41
 */
public class TagUtil {
    public static List<TagDTO> getTags() {
        List<TagDTO> tags = new ArrayList<>();
        TagDTO languages = new TagDTO();
        languages.setCategory("语言");
        languages.setTags(Arrays.asList(
            "Java", "C", "Python", "C#", "Go", "PHP", "Matlab", "C++", "Kotlin", "Ruby"
        ));
        tags.add(languages);

        TagDTO ai = new TagDTO();
        ai.setCategory("人工智能");
        ai.setTags(Arrays.asList(
                "微服务", "区块链", "以太坊", "人工智能", "机器学习", "深度学习", "计算机视觉", "自然语言处理", "数据分析&挖掘"
        ));
        tags.add(ai);
        TagDTO frontEnd = new TagDTO();
        frontEnd.setCategory("前端");
        frontEnd.setTags(Arrays.asList(
                "HTML/CSS", "JavaScript", "Vue.js", "React.JS", "Angular", "Node.js", "Bootstrap", "jQuery", "WebApp"
        ));
        tags.add(frontEnd);

        TagDTO framework = new TagDTO();
        framework.setCategory("框架");
        framework.setTags(Arrays.asList(
                "SpringBoot", "Spring Cloud", "Spring MVC", "Mybatis", "Hadoop", "Django", "Tornado","Spark"
        ));
        tags.add(framework);

        TagDTO database = new TagDTO();
        database.setCategory("数据库与缓存");
        database.setTags(Arrays.asList(
                "MySQL", "Redis", "MongoDB", "Oracle", "SQL Server", "NoSql"
        ));
        tags.add(database);

        TagDTO tools = new TagDTO();
        tools.setCategory("开发工具");
        tools.setTags(Arrays.asList(
                "Git", "Github", "Visual Studio", "VS Code", "Intellij IDEA", "Eclipse", "Maven", "Sublime text"
        ));
        tags.add(tools);
        return tags;
    }
}