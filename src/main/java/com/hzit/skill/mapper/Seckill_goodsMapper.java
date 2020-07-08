package com.hzit.skill.mapper;

import com.hzit.skill.model.Seckill_goods;
import org.springframework.stereotype.Repository;

@Repository
public interface Seckill_goodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Seckill_goods record);

    int insertSelective(Seckill_goods record);

    Seckill_goods selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Seckill_goods record);

    int updateByPrimaryKey(Seckill_goods record);
}