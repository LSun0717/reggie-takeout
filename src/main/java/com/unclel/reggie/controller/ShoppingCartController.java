package com.unclel.reggie.controller;

import com.unclel.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ShoppingCartController
 * @Description TODO 购物车Controller
 * @Author uncle_longgggggg
 * @Date 7/3/2022 2:24 PM
 * @Version 1.0
 */

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;


}