package com.unclel.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.unclel.reggie.entity.AddressBook;
import com.unclel.reggie.mapper.AddressBookMapper;
import com.unclel.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @ClassName AddressBookServiceImpl
 * @Description TODO 地址簿业务接口实现类
 * @Author uncle_longgggggg
 * @Date 7/3/2022 11:37 AM
 * @Version 1.0
 */

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}