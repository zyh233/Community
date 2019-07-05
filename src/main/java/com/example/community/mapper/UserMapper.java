package com.example.community.mapper;

import com.example.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    User getUserById(Integer id);

    @Select("select * from user where name=#{username}")
    User getUserByUserName(String username);

    @Delete("delete from user where id = #{id}")
    int deleteUserById(Integer id);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into user (name, account_id, token, gmt_create, gmt_modified) values(#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified})")
    int insertUser(User user);

    @Update("update user set name=#{name}, age=#{age}, email=#{email}, birth=#{birth} where id=#{id}")
    int updateUser(User user);

    @Select("select * from user where token = #{token}")
    User getUserByToken(String token);
}
