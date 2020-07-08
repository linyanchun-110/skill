package com.hzit.skill.resp;

import com.hzit.skill.model.User_info;
import lombok.Data;


@Data
public class RespLogin<T> {
    private int code;
    private String msg;
    private T data;
    private long remainSeconds;
    private User_info user;
}
