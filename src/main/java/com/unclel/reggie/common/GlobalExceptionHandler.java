package com.unclel.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @ClassName GlobalExceptionHandler
 * @Description 全局异常处理
 * @Author uncle_longgggggg
 * @Date 6/28/2022 4:49 PM
 * @Version 1.0
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /*
    * @description:异常处理方法

    * @return: * @return R<String>
    * @author: uncle_longgggggg
    * @time: 6/28/2022 4:55 PM
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        log.error(ex.getMessage());
        // 处理新建用户时，username同名与mysql username字段唯一而产生的冲突
        if (ex.getMessage().contains("Duplicate entry")) {
            String[] strings = ex.getMessage().split(" ");
            String msg = strings[2] + " 已存在";
            return R.error(msg);
        }

        return R.error("未知错误");
    }


}