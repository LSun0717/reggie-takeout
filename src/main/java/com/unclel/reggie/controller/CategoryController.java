package com.unclel.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unclel.reggie.common.R;
import com.unclel.reggie.entity.Category;
import com.unclel.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName CategoryController
 * @Description TODO
 * @Author uncle_longgggggg
 * @Date 6/29/2022 3:07 PM
 * @Version 1.0
 */

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /*
    * @description:新增分类
    * @param category
    * @return: * @return R<String>
    * @author: uncle_longgggggg
    * @time: 6/29/2022 3:21 PM
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("新增分类:{}", category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }
    /*
    * @description:分类表的分页查询
    * @param page
    * @param pageSize
    * @return: * @return R<Page>
    * @author: uncle_longgggggg
    * @time: 6/29/2022 3:33 PM
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
        log.info("分类分页查询，{}，{}", page ,pageSize);

        Page<Category> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Category> pageLambdaQueryWrapper = new LambdaQueryWrapper<>();
        pageLambdaQueryWrapper.orderByAsc(Category::getSort);
        categoryService.page(pageInfo, pageLambdaQueryWrapper);

        return R.success(pageInfo);
    }

    /*
    * @description:根据id删除分类
    * @param id
    * @return: * @return R<String>
    * @author: uncle_longgggggg
    * @time: 6/29/2022 4:09 PM
     */
    @DeleteMapping
    public R<String> delete(Long ids) {
        log.info("删除分类,id：", ids);
        categoryService.remove(ids);
        return R.success("分类信息删除成功");
    }

    /*
    * @description:修改分类信息
    * @param category
    * @return: * @return R<String>
    * @author: uncle_longgggggg
    * @time: 6/30/2022 1:42 PM
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        log.info("修改分类信息:{}", category);

        categoryService.updateById(category);
        return R.success("修改分类信息成功");
    }

    /*
    * @description:根据条件查询分类数据
    * @param category
    * @return: * @return R<List<Category>>
    * @author: uncle_longgggggg
    * @time: 6/30/2022 3:32 PM
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        // 条件构造器
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加条件
        categoryLambdaQueryWrapper.eq(category.getType() != null , Category::getType, category.getType());
        // 添加排序条件
        categoryLambdaQueryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        //
        List<Category> list = categoryService.list(categoryLambdaQueryWrapper);

        return R.success(list);

    }

}