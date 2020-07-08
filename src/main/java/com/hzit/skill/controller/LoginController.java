package com.hzit.skill.controller;

import com.hzit.skill.model.User_info;
import com.hzit.skill.resp.RespLogin;
import com.hzit.skill.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private RedisTemplate redisTemplate;
    private static final Logger logger= LoggerFactory.getLogger(LoginController.class);
    @RequestMapping("/login2")
    public String login(){
       logger.info("加载登录页面");
        return "login";
    }
    @ResponseBody
    @RequestMapping("/user/login")
    public RespLogin login1(@RequestParam("phone") String phone){
        logger.info("接收到手机号:{}登录的请求",phone);
        RespLogin resp=new RespLogin();
        List<User_info> list=loginService.queryUser(phone);
        int rlt=list.size();

        //放入缓存中
       HashOperations<String,String,Object> hashOperations =redisTemplate.opsForHash();
       String id=list.get(0).getId().toString();
       String userName=list.get(0).getUserName();
       String redisKey="UserRedisKey"+':'+id;
       hashOperations.put(redisKey,id,list.get(0));
       //设置缓存有效期
        redisTemplate.expire(redisKey,2, TimeUnit.HOURS);

        if(rlt>0){
            resp.setCode(0);
            resp.setMsg("成功");
        }else{
            resp.setCode(-1);
            resp.setMsg("失败");
        }
        //返回给前端的用户信息
        User_info user_info=new User_info();
        user_info.setId(Integer.parseInt(id));
        user_info.setUserName(userName);
        resp.setData(user_info);
        return resp;
    }

}
