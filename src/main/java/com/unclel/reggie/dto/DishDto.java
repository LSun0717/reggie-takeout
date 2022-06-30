package com.unclel.reggie.dto;

import com.unclel.reggie.entity.Dish;
import com.unclel.reggie.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/*
* @description:DTO for add new dish
* @param null
* @return:
* @author: uncle_longgggggg
* @time: 6/30/2022 4:40 PM
 */
@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
