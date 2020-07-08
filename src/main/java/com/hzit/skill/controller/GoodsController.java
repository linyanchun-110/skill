package com.hzit.skill.controller;

import com.hzit.skill.req.GoodsData;
import com.hzit.skill.service.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class GoodsController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private GoodsService goodsService;
    private static final Logger logger= LoggerFactory.getLogger(LoginController.class);
    @RequestMapping("/goods/list")
    public String index(Model model){
        logger.info("跳转到商品列表页面");
       // 定义一个list后面要将查出的数据放入缓存中
        ListOperations<String,List<GoodsData>> listListOperations=redisTemplate.opsForList();
        //查询数据库之前先从缓存中拿
        List<GoodsData> goodsDataList1=listListOperations.rightPop("redisGoodsListKey");
        //提出来是为了放入model中
        List<GoodsData> goodsDataList=null;
        if(CollectionUtils.isEmpty(goodsDataList1)){
            goodsDataList=goodsService.queryAllGoods();
            //将查到的数据放入缓存中
            listListOperations.leftPushAll("redisGoodsListKey",goodsDataList);
            //设置过期时间
            redisTemplate.expire("redisGoodsListKey",2, TimeUnit.HOURS);


            model.addAttribute("goodsList",goodsDataList);
        }else {
            model.addAttribute("goodsList",goodsDataList1);
        }

        return "goods_list";
    }
}
