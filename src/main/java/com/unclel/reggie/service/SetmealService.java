package com.unclel.reggie.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.unclel.reggie.dto.SetmealDto;
import com.unclel.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    /*
    * @description:新增套餐，同时需要保存套餐和菜品的关联关系
    * @param setmealDto
    * @return:
    * @author: uncle_longgggggg
    * @time: 7/1/2022 11:23 AM
     */
    void saveWithDish(SetmealDto setmealDto);

    /*删除套餐，同时删除套餐和菜品的关联关系
    * @description:
    * @param ids
    * @return:
    * @author: uncle_longgggggg
    * @time: 7/1/2022 11:24 AM
     */
    void removeWithDish(List<Long> ids);
}
