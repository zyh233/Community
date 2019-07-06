package com.example.community.mapper;

import com.example.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (id, title, description, gmt_create, gmt_modified, creator, tag) " +
            "values(#{id}, #{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    void create(Question question);

    @Select("select * from question")
    List<Question> getQuestions();

    @Select("select * from question limit #{page}, #{size}")
    List<Question> getQuestionByPage(Integer page, Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select count(1) from question where creator = #{id}")
    int countById(Integer id);

    @Select("select * from question where creator = #{id} limit #{page}, #{size}")
    List<Question> getQuestionByCreator(Integer id, Integer page, Integer size);

    @Select("select * from question where id = #{id}")
    Question getById(Integer id);
}
