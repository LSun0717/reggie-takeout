package com.unclel.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unclel.reggie.common.CustomException;
import com.unclel.reggie.entity.Category;
import com.unclel.reggie.entity.Dish;
import com.unclel.reggie.entity.Setmeal;
import com.unclel.reggie.mapper.CategoryMapper;
import com.unclel.reggie.service.CategoryService;
import com.unclel.reggie.service.DishService;
import com.unclel.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName CategoryServiceImpl
 * @Description TODO
 * @Author uncle_longgggggg
 * @Date 6/29/2022 3:06 PM
 * @Version 1.0
 */
@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /*
     * @description:删除分类时先进行菜品及套餐是否关联校验
     * @param id
     * @return:
     * @author: uncle_longgggggg
     * @time: 6/29/2022 4:54 PM
     */
    @Override
    public void remove(Long id) {

        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件，根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        if (count1 > 0) {
            throw new CustomException("当前分类下已关联菜品，无法删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件，根据分类id进行查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);

        log.info(count1 + "--------hhh----" + count2);
        if (count2 > 0) {
            throw new CustomException("当前分类下已关联套餐，无法删除");
        }

        super.removeById(id);

    }
}