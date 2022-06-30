package com.unclel.reggie.common;

/**
 * @ClassName CustomException
 * @Description 自定义异常
 * @Author uncle_longgggggg
 * @Date 6/29/2022 4:46 PM
 * @Version 1.0
 */
public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}