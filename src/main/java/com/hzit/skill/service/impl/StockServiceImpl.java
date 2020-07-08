package com.hzit.skill.service.impl;

import com.hzit.skill.mapper.GoodsMapper;
import com.hzit.skill.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public void DcrStock(Long goodsId) {
       goodsMapper.DcrStock(goodsId);
    }
}
