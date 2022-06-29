package com.unclel.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unclel.reggie.entity.Category;
import com.unclel.reggie.mapper.CategoryMapper;
import com.unclel.reggie.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * @ClassName CategoryServiceImpl
 * @Description TODO
 * @Author uncle_longgggggg
 * @Date 6/29/2022 3:06 PM
 * @Version 1.0
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}