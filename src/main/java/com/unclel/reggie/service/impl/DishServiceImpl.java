package com.unclel.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unclel.reggie.dto.DishDto;
import com.unclel.reggie.entity.Dish;
import com.unclel.reggie.entity.DishFlavor;
import com.unclel.reggie.mapper.DishMapper;
import com.unclel.reggie.service.DishFlavorService;
import com.unclel.reggie.service.DishService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName DishServiceImpl
 * @Description TODO
 * @Author uncle_longgggggg
 * @Date 6/29/2022 4:28 PM
 * @Version 1.0
 */
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
            // 保存菜品的基本信息到菜品表dish
            this.save(dishDto);
            // 将dishDtoId 传递给 dishId
            Long dishId = dishDto.getId();
            // 循环遍历，为List中每个元素的dishId赋值
            List<DishFlavor> flavors = dishDto.getFlavors();
            flavors.stream().map((item) -> {
                item.setDishId(dishId);
                return item;
            }).collect(Collectors.toList());

            dishFlavorService.saveBatch(flavors);

    }
}