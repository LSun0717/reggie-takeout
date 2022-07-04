package com.unclel.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.unclel.reggie.common.BaseContext;
import com.unclel.reggie.common.R;
import com.unclel.reggie.entity.ShoppingCart;
import com.unclel.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

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


    /*
    * @description:添加购物车
    * @param shoppingCart
 * @param httpSession
    * @return: * @return R<ShoppingCart>
    * @author: uncle_longgggggg
    * @time: 7/4/2022 1:10 PM
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart, HttpSession httpSession) {
        log.info("购物车数据：{}", shoppingCart.toString());
        // 设置用户id
        Long currentUserId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentUserId);

        // 查询当前菜品或者套餐是否已经在购物车中
        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();

        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId, shoppingCart.getUserId());

        if (dishId != null) {
            // 添加到购物车的是菜品
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getDishId, dishId);
        }else {
            // 添加到购物车的是套餐
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        ShoppingCart existedInShoppingCart = shoppingCartService.getOne(shoppingCartLambdaQueryWrapper);
        // 判断菜品是否已在购物车，在则加1，反之新建
        if (existedInShoppingCart != null ) {
            int dishNum = existedInShoppingCart.getNumber();
            existedInShoppingCart.setNumber(dishNum + 1);
            shoppingCartService.updateById(existedInShoppingCart);
        } else {
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            existedInShoppingCart = shoppingCart;
        }

        return R.success(existedInShoppingCart);
    }

    /*
    * @description:查看购物车业务接口

    * @return: * @return R<List<ShoppingCart>>
    * @author: uncle_longgggggg
    * @time: 7/4/2022 1:14 PM
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {

        log.info("查看购物车");
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 根据userid查询购物车，并按照时间升序排列
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        shoppingCartLambdaQueryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(shoppingCartLambdaQueryWrapper);
        return R.success(shoppingCartList);
    }

    /*
    * @description:清空购物车

    * @return: * @return R<String>
    * @author: uncle_longgggggg
    * @time: 7/4/2022 1:20 PM
     */
    @DeleteMapping("/clean")
    public R<String> clean() {
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        shoppingCartService.remove(shoppingCartLambdaQueryWrapper);
        return R.success("购物车已清空");
    }

}