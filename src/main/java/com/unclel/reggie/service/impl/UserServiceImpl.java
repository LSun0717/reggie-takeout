package com.unclel.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unclel.reggie.entity.User;
import com.unclel.reggie.mapper.UserMapper;
import com.unclel.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @ClassName UserServiceImpl
 * @Description TODO 手机端User业务
 * @Author uncle_longgggggg
 * @Date 7/1/2022 1:40 PM
 * @Version 1.0
 */

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}