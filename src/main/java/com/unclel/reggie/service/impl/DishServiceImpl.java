package com.unclel.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unclel.reggie.dto.DishDto;
import com.unclel.reggie.entity.Dish;
import com.unclel.reggie.entity.DishFlavor;
import com.unclel.reggie.mapper.DishMapper;
import com.unclel.reggie.service.DishFlavorService;
import com.unclel.reggie.service.DishService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

    /*
    * @description:根据id查询DishDto数据返回
    * @param id
    * @return: * @return DishDto
    * @author: uncle_longgggggg
    * @time: 6/30/2022 8:42 PM
     */
    @Override
    public DishDto getByIdWithFlavour(Long id) {

        Dish dish = this.getById(id);

        // dish与dishDto中差的就是Flavours
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(dishFlavorLambdaQueryWrapper);

        dishDto.setFlavors(flavors);
        return dishDto;
    }

    /*
    * @description:更新菜品信息及口味信息
    * @param dishDto
    * @return:
    * @author: uncle_longgggggg
    * @time: 6/30/2022 9:11 PM
     */
    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        // 更新dish基本信息
        this.updateById(dishDto);

        // 删除原始口味信息
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(dishFlavorLambdaQueryWrapper);

        List<DishFlavor> flavors = dishDto.getFlavors();

        // 设置id
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        // 保存新的口味信息
        dishFlavorService.saveBatch(flavors);

    }

}