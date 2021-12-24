package com.funicorn.basic.common.datasource.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.funicorn.basic.common.base.model.UserDetail;
import com.funicorn.basic.common.base.util.ContextHolder;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


/**
 * @author Aimee
 * @since 2020/8/4 10:38
 * 处理新增和更新的基础数据填充
 */
@Component
public class MetaHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        UserDetail userDetail = ContextHolder.getCurrentUser();
        if (userDetail !=null){
            this.setFieldValByName("createdBy", userDetail.getUsername(), metaObject);
        }
        this.setFieldValByName("createdTime", LocalDateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        UserDetail userDetail = ContextHolder.getCurrentUser();
        if (userDetail !=null){
            this.setFieldValByName("updatedBy", userDetail.getUsername(), metaObject);
        }
        this.setFieldValByName("updatedTime", LocalDateTime.now(), metaObject);
    }
}
