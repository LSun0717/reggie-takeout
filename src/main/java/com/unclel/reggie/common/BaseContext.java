package com.unclel.reggie.common;

/**
 * @ClassName BaseContext
 * @Description 基于ThreadLocal封装工具类，在此线程中共享数据，用户保存和获取当前登录用户的id
 * @Author uncle_longgggggg
 * @Date 6/29/2022 2:20 PM
 * @Version 1.0
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
}