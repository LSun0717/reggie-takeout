package com.unclel.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unclel.reggie.common.R;
import com.unclel.reggie.dto.DishDto;
import com.unclel.reggie.entity.Category;
import com.unclel.reggie.entity.Dish;
import com.unclel.reggie.entity.DishFlavor;
import com.unclel.reggie.service.CategoryService;
import com.unclel.reggie.service.DishFlavorService;
import com.unclel.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * @ClassName DishController
 * @Description TODO 菜品控制
 * @Author uncle_longgggggg
 * @Date 6/30/2022 3:14 PM
 * @Version 1.0
 */

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    /*
    * @description:新增菜品信息
    * @param dishDto
    * @return: * @return R<String>
    * @author: uncle_longgggggg
    * @time: 6/30/2022 4:36 PM
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {

        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        // 构造分页构造器对象
        Page<Dish> dishPage = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        // 条件构造器
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件
        dishLambdaQueryWrapper.like(name != null, Dish::getName, name);
        // 添加排序条件
        dishLambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);
        // 执行分页查询
        dishService.page(dishPage, dishLambdaQueryWrapper);

        // 对象拷贝,除dishPage records属性以外
        BeanUtils.copyProperties(dishPage, dishDtoPage, "records");

//      1.item为一个个dish对象
//      2.通过item拿到categoryId
//      3.通过categoryId调用categoryService拿到category对象
//      4.通过category拿到categoryName
//      5.为dishDto设置categoryName
        List<Dish> records = dishPage.getRecords();
        List<DishDto> dishDtoList = records.stream().map((item) -> {

            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            return dishDto;

        }).collect(Collectors.toList());

        dishDtoPage.setRecords(dishDtoList);

        return R.success(dishDtoPage);
    }

    /*
     * @description:根据id获取指定dto
     * @param id
     * @return: * @return R<DishDto>
     * @author: uncle_longgggggg
     * @time: 6/30/2022 8:38 PM
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavour(id);
        return R.success(dishDto);
    }

    /*
    * @description:修改菜品信息
    * @param dishDto
    * @return: * @return R<String>
    * @author: uncle_longgggggg
    * @time: 6/30/2022 9:11 PM
     */
    @PutMapping
    public R<String> updateWithFlavour(@RequestBody DishDto dishDto) {

        log.info(dishDto.toString());
        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
    }

}