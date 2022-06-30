package com.unclel.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unclel.reggie.entity.DishFlavor;
import com.unclel.reggie.mapper.DishFlavorMapper;
import com.unclel.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * @ClassName DishFlavorServiceImpl
 * @Description TODO
 * @Author uncle_longgggggg
 * @Date 6/30/2022 3:10 PM
 * @Version 1.0
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}