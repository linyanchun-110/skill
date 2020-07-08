package com.hzit.skill.service;

import com.hzit.skill.model.User_info;

import java.util.List;

public interface LoginService {
    public List<User_info> queryUser(String phone);
    public List<User_info> queryUserById(Integer userId);
}
