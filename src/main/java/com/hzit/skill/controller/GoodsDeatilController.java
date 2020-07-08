package com.hzit.skill.controller;


import com.hzit.skill.model.User_info;
import com.hzit.skill.req.GoodsDeatil;
import com.hzit.skill.resp.RespLogin;
import com.hzit.skill.service.GoodsDeatilService;

import com.hzit.skill.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class GoodsDeatilController {
    private static final Logger logger = LoggerFactory.getLogger(GoodsDeatilController.class);
    //将string类型的时间字符串转换
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
    @Autowired
    private GoodsDeatilService goodsDeatilService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private RedisTemplate redisTemplate;
    @ResponseBody
    @RequestMapping("/goods/detail")
    //和@Pathvaible相同的是都可以从请求地址上获取参数传到控制器，请求方式都为get，不同的是注意拼接地址方式，path还要拼在映射地址上
    //@RequestParam还可以在get请求中获取参数，请求下面定义一个data来获取前端参数，还有post中的body
    public RespLogin<GoodsDeatil> queryGoodsDeatil(@RequestParam("goodsId") Long goodsId,
                                                   @RequestParam("userId") Integer userId){
        logger.info("接收到查询商品详细信息的请求");
        RespLogin<GoodsDeatil> respLogin = new RespLogin<GoodsDeatil>();
        if(StringUtils.isEmpty(goodsId.toString())){
            return null;
        }

        //把查询到的商品详情放入到缓存中
        HashOperations<String,String,GoodsDeatil> hashOperations=redisTemplate.opsForHash();
        String redisKey="DredisKey";
        GoodsDeatil goodsDeatil=hashOperations.get(redisKey,goodsId.toString());
        GoodsDeatil goodsDeatil1=null;
        if(ObjectUtils.isEmpty(goodsDeatil)){

            goodsDeatil1=goodsDeatilService.queryGoodsDetail(goodsId);
              hashOperations.put(redisKey,goodsId.toString(),goodsDeatil1);
              redisTemplate.expire(redisKey,2, TimeUnit.HOURS);
              respLogin.setData(goodsDeatil1);
        }else {
            respLogin.setData(goodsDeatil);
        }


        //计算距离秒杀活动是否开启和剩余时间
        try {
            //string类型转换为date类型
            Date startTime = format.parse(goodsDeatil.getStartDate());
            Date endTime = format.parse(goodsDeatil.getEndDate());
            //获取系统时间
            long date = new Date().getTime();
            //date类型转换为long类型用于后面比较
            long startTimes=startTime.getTime();
            long endTimes=endTime.getTime();
            logger.info("开始时间为{}",startTimes);
            logger.info("结束时间为{}",endTimes);
            logger.info("系统时间为:{}",date);
            if(date>startTimes && date<endTimes){
                respLogin.setRemainSeconds(0);
            }else if(startTimes>date){
                long remainSeconds=startTimes-date;
                logger.info("距离秒杀时间还有{}秒",remainSeconds);
                respLogin.setRemainSeconds(remainSeconds);//把值带过countDown方法去，大于O，开始倒计时
            }else{
                respLogin.setRemainSeconds(0);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        //返回给前端的用户信息，让页面显示哪个用户在进行秒杀，放入缓存中，后面还要加入订单上
        HashOperations<String,String,User_info> hashOperations1=redisTemplate.opsForHash();
        String redisKey1="UserRedisKey"+':'+userId;
        User_info user_info=hashOperations1.get(redisKey1,userId.toString());
        if(ObjectUtils.isEmpty(user_info)){
            List<User_info> list=loginService.queryUserById(userId);
            respLogin.setUser(list.get(0));
        }
        respLogin.setUser(user_info);

        logger.info("查询的商品详情信息为：{}", goodsDeatil);
        respLogin.setCode(0);
        respLogin.setMsg("成功");
        return respLogin;



    }

}




