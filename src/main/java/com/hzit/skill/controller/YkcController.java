package com.hzit.skill.controller;

import com.hzit.skill.req.GoodsData;
import com.hzit.skill.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 预库存
 */
@RestController
public class YkcController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping("/localStock")
    public String getLocalStock(){
        List<GoodsData> goodsDataList=goodsService.queryAllGoods();
        ValueOperations<String,Integer> valueOperations=redisTemplate.opsForValue();
        if(CollectionUtils.isEmpty(goodsDataList)){
           return "没有商品";
        }
        for(GoodsData g:goodsDataList){
            String redisKey="Goods_stock:"+g.getId();
            valueOperations.set(redisKey,g.getGoodsStock());
        }
        return "success";
    }
}
