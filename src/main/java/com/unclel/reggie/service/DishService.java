package com.unclel.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.unclel.reggie.dto.DishDto;
import com.unclel.reggie.entity.Dish;

public interface DishService extends IService<Dish> {

    // 新增菜品，同时插入菜品对应的口味数据，需要操作两张表，dish、dishDto
    void saveWithFlavor(DishDto dishDto);

    //  根据id查询DishDto数据返回
    DishDto getByIdWithFlavour(Long id) ;

    // 更新菜品信息和口味信息
    void updateWithFlavor(DishDto dishDto);
}
