package com.example.community.service;

import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import com.example.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper mapper;

//    public void insertUser(User user) {
//        mapper.insertUser(user);
//
//    }

//    public User getUserByToken(String token) {
//        return mapper.getUserByToken(token);
//    }
//
//    public User getUserByAccountId(String id) {
//        User user = mapper.getUserByAccountId(id);
//        return user;
//    }

    public void createOrUpdate(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = mapper.selectByExample(example);


//        User db = mapper.getUserByAccountId(String.valueOf(user.getAccountId()));
        if (users.size() == 0) {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            mapper.insert(user);
        } else {
            User db = users.get(0);
            user.setGmtModified(System.currentTimeMillis());
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(db.getId());
            mapper.updateByExampleSelective(user, userExample);
        }
    }
}
