package com.hzit.skill.mapper;

import com.hzit.skill.model.Order_info;
import org.springframework.stereotype.Repository;

@Repository
public interface Order_infoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order_info record);

    int insertSelective(Order_info record);

    Order_info selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order_info record);

    int updateByPrimaryKey(Order_info record);


}