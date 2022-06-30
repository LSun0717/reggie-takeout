package com.unclel.reggie.controller;

import com.unclel.reggie.common.R;
import com.unclel.reggie.dto.DishDto;
import com.unclel.reggie.entity.DishFlavor;
import com.unclel.reggie.service.DishFlavorService;
import com.unclel.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DishController
 * @Description TODO 菜品控制
 * @Author uncle_longgggggg
 * @Date 6/30/2022 3:14 PM
 * @Version 1.0
 */

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

}