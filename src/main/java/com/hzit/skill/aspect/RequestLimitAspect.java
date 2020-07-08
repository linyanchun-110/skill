package com.hzit.skill.aspect;

import com.hzit.skill.anno.RequestLimit;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
public class RequestLimitAspect {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     *
     */
    @Pointcut("execution(public * com.hzit.skill.controller.*.*(..))")
    public void fangshua(){

    }
    @Before("fangshua()&&@annotation(limit)")
    public void requestLimit(JoinPoint joinPoint,RequestLimit limit){

        //读到注解里面的参数
        System.out.println(limit.count());
//        System.out.println(limit.time());
        //获取用户ip
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =attributes.getRequest();
        //获取到用户请求ip和url
        String ip=request.getRemoteAddr();
        String url=request.getRequestURL().toString();
        //用户请求次数的key
        String key="req_limit_".concat(url).concat(ip);
        long count=redisTemplate.opsForValue().increment(key,1);
        //从1开始设置过期时间
        if(count==1){
            redisTemplate.expire(key,limit.time(),TimeUnit.MILLISECONDS);
        }
        if(count>limit.count()){
            System.out.println("用户ip["+ip+"]用户地址["+url+"]超过了限定次数["+limit.count()+"]");
            throw  new RuntimeException("超过限制访问次数");
        }



    }
}
