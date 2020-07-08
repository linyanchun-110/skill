package com.hzit.skill.mapper;

import com.hzit.skill.model.User_info;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface User_infoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User_info record);

    int insertSelective(User_info record);

    User_info selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User_info record);

    int updateByPrimaryKey(User_info record);

    List<User_info> queryUser(String phone);

    List<User_info> queryUserById(Integer userId);
}