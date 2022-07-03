package com.unclel.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unclel.reggie.common.R;
import com.unclel.reggie.dto.SetmealDto;
import com.unclel.reggie.entity.Category;
import com.unclel.reggie.entity.Setmeal;
import com.unclel.reggie.service.CategoryService;
import com.unclel.reggie.service.SetmealDishService;
import com.unclel.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName SetmealDishController
 * @Description 套餐管理
 * @Author uncle_longgggggg
 * @Date 6/30/2022 9:41 PM
 * @Version 1.0
 */

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {


//    @Autowired
//    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    /*
    * @description:新增套餐
    * @param setmealDto
    * @return: * @return R<String>
    * @author: uncle_longgggggg
    * @time: 7/1/2022 9:56 AM
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
//        log.info(setmealDto.toString());
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        Page<Setmeal> setmealPage = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.like(name != null, Setmeal::getName, name);
        setmealLambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(setmealPage, setmealLambdaQueryWrapper);
        BeanUtils.copyProperties(setmealPage, setmealDtoPage,"records");

        List<Setmeal> records = setmealPage.getRecords();

        // Setmeal中无categoryName，所以需要以下操作
        List<SetmealDto> setmealDtoList = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(setmealDtoList);
        return R.success(setmealDtoPage);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {

        log.info(ids.toString());
        setmealService.removeWithDish(ids);

        return R.success("删除成功");
    }

    /*
    * @description:客户端首页展示套餐信息
    * @param setmeal
    * @return: * @return R<List<Setmeal>>
    * @author: uncle_longgggggg
    * @time: 7/3/2022 1:06 PM
     */
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId,setmeal.getCategoryId());
        setmealLambdaQueryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus,setmeal.getStatus());

        List<Setmeal> setmealList = setmealService.list(setmealLambdaQueryWrapper);

        return R.success(setmealList);
    }
}