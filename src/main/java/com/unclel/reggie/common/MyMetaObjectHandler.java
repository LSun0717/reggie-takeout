package com.unclel.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @ClassName MyMetaObjectHandler
 * @Description 自定义元数据处理器，用于公共字段自动填充
 * @Author uncle_longgggggg
 * @Date 6/29/2022 12:49 PM
 * @Version 1.0
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    /*
    * @description:插入时自动填充
    * @param metaObject
    * @return:
    * @author: uncle_longgggggg
    * @time: 6/29/2022 1:58 PM
     */
    public void insertFill(MetaObject metaObject) {

        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", BaseContext.getCurrentId());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());

    }

    /*
    * @description:更新时自动填充
    * @param metaObject
    * @return:
    * @author: uncle_longgggggg
    * @time: 6/29/2022 2:03 PM
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }
}