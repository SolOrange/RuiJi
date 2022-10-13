package com.example.ruiji.Common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    //插入时自动操作
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", new Date());
        metaObject.setValue("updateTime", new Date());
        metaObject.setValue("createUser", BaseContext.getCurrentId());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());

    }

    //更新时自动操作
    @Override
    public void updateFill(MetaObject metaObject) {
        Long id = Thread.currentThread().getId();

        metaObject.setValue("updateTime", new Date());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }
}
