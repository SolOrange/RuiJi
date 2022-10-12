package com.example.ruiji.Common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 基于ThreadLocal的封装工具累，用户获取当前登陆用户的id
 */
@Slf4j
public class BaseContext {
    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();

    public static void setCurrentID(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
