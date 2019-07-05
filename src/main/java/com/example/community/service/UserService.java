package com.example.community.service;

import com.example.community.mapper.UserMapper;
import com.example.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper mapper;

    public void insertUser(User user) {
        mapper.insertUser(user);

    }

    public User getUserByToken(String token) {
        return mapper.getUserByToken(token);
    }
}
