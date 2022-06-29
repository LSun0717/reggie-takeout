package com.unclel.reggie.controller;

import com.unclel.reggie.common.R;
import com.unclel.reggie.entity.Category;
import com.unclel.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}