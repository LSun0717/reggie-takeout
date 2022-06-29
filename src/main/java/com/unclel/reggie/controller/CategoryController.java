package com.unclel.reggie.controller;

import com.unclel.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/Category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


}