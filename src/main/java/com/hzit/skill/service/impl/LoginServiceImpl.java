package com.hzit.skill.service.impl;

import com.hzit.skill.mapper.User_infoMapper;
import com.hzit.skill.model.User_info;
import com.hzit.skill.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private User_infoMapper user_infoMapper;

    @Override
    public List<User_info> queryUserById(Integer userId) {
        return null;
    }

    @Override
    public List<User_info> queryUser(String phone) {

        return user_infoMapper.queryUser(phone);

    }
}
