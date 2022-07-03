package com.unclel.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unclel.reggie.entity.ShoppingCart;
import com.unclel.reggie.mapper.ShoppingCartMapper;
import com.unclel.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @ClassName ShoppingCartServiceImpl
 * @Description TODO 购物车业务接口
 * @Author uncle_longgggggg
 * @Date 7/3/2022 2:22 PM
 * @Version 1.0
 */

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}