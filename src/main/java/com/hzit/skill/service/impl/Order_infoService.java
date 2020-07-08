package com.hzit.skill.service.impl;

import com.hzit.skill.mapper.GoodsMapper;
import com.hzit.skill.mapper.Order_infoMapper;
import com.hzit.skill.mapper.Seckill_orderMapper;
import com.hzit.skill.model.Order_info;
import com.hzit.skill.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
@Service
public class Order_infoService implements OrderService {
    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private Order_infoMapper order_InfoMapper;

    @Autowired
    private Seckill_orderMapper seckill_orderMapp;
    @Override
    @Transactional
    public void createOrder(int userId,long goodsId) {
        Order_info orderInfo = new Order_info();
        orderInfo.setAddrId(01l);
        orderInfo.setCreateDate(new Date());
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsPrice(new BigDecimal(100));
        orderInfo.setStatus(0);
        orderInfo.setGoodsId(goodsId);
//        orderInfo.setUserId(userId);数据库表设计不行，懒得改类型
        //添加订单
        order_InfoMapper.insertSelective(orderInfo);
        //减少库存
         goodsMapper.DcrStock(goodsId);
//         seckill_orderMapp.DcrStock(goodsId);不需要减少秒杀库存，放在了redis预库存中，减少数据库压力
    }
}
