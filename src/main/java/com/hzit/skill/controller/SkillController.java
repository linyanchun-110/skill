package com.hzit.skill.controller;

import com.hzit.skill.resp.RespLogin;
import com.hzit.skill.service.OrderService;
import com.hzit.skill.service.impl.Order_infoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.UUID;

@Controller
@RequestMapping("/seckill")
public class SkillController {
    private static final Logger logger= LoggerFactory.getLogger(SkillController.class);
    @Autowired
    private RedisTemplate redisTemplate;
     @Autowired
     private OrderService orderService;
    /**
     * 生成隐藏地址
     * @param userId
     * @param goodsId
     * @return
     */
    @ResponseBody
    @RequestMapping("/path")
    public RespLogin getPath(@RequestParam("userId") String userId,
                             @RequestParam("goodsId") String goodsId){
        logger.info("生成一个隐藏地址：");
        RespLogin respLogin=new RespLogin();
        String path= UUID.randomUUID().toString().replace("_","");
        ValueOperations<String,String> valueOperations=redisTemplate.opsForValue();
        String redisKey="redisKey:"+userId+goodsId;
        valueOperations.set(redisKey,path);//放入缓存中的目的是在开始秒杀时候判断生成隐藏地址带过去给秒杀方法的地址和缓存中的是否一致
        respLogin.setCode(0);
        respLogin.setMsg("成功");
        respLogin.setData(path);
        return respLogin;
    }

    /**
     * 开始秒杀
     * @param path
     * @param userId
     * @param goodsId
     * @return
     */
    @ResponseBody
    @RequestMapping("/{path}/seckill/{userId}/{goodsId}")
    public RespLogin startSkill(@PathVariable("path") String path,
                                @PathVariable("userId") int userId,
                                @PathVariable("goodsId") long goodsId){
        RespLogin respLogin=new RespLogin();
        logger.info("接收到用户{}秒杀商品{}请求:",userId,goodsId);
        String redisKey="redisKey:"+userId+goodsId;
        ValueOperations<String,String> valueOperations=redisTemplate.opsForValue();
        String redisPath=valueOperations.get(redisKey);
        if(StringUtils.isEmpty(redisPath)){
           respLogin.setCode(-1);
           respLogin.setMsg("请求路径有误");
            return respLogin;
        }
        //判断请求是否非法
        if(!redisPath.equals(path)){
            respLogin.setCode(-1);
            respLogin.setMsg("请求非法");
            return respLogin;
        }
        //判断库存是否足够
        ValueOperations<String,Integer> valueOperations1=redisTemplate.opsForValue();
        String redisKey1="Goods_stock:"+goodsId;
        long stock=valueOperations1.decrement(redisKey1,1l);
        if(stock==0){
            respLogin.setCode(-1);
            respLogin.setMsg("商品库存不足");
            return respLogin;
        }
        //是否重复秒杀

        //生成订单，减库存
        orderService.createOrder(userId,goodsId);
        respLogin.setCode(0);
        respLogin.setMsg("秒杀成功");
        return respLogin;
    }
}
