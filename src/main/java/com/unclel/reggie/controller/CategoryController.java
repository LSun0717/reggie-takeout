package com.unclel.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unclel.reggie.common.R;
import com.unclel.reggie.entity.Category;
import com.unclel.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        log.info("category:{}", category);
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
    public R<String> delete(Long id) {

        return null;
    }

}