package com.unclel.reggie.dto;

import com.unclel.reggie.entity.Setmeal;
import com.unclel.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
