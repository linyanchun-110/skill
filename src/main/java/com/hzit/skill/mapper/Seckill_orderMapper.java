package com.hzit.skill.mapper;

import com.hzit.skill.model.Seckill_order;
import org.springframework.stereotype.Repository;

@Repository
public interface Seckill_orderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Seckill_order record);

    int insertSelective(Seckill_order record);

    Seckill_order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Seckill_order record);

    int updateByPrimaryKey(Seckill_order record);

    void DcrStock(Long goodsId);
}