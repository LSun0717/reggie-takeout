package com.unclel.reggie.controller;

import com.unclel.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName CommonController
 * @Description TODO
 * @Author uncle_longgggggg
 * @Date 6/30/2022 1:55 PM
 * @Version 1.0
 */

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    /*
    * @description:上传文件
    * @param file
    * @return: * @return R<String>
    * @author: uncle_longgggggg
    * @time: 6/30/2022 2:22 PM
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        // file为临时目录下文件，必须在请求结束之前进行转存
        log.info(file.toString());
        // 获取原文件名
        String originalFilename = file.getOriginalFilename();
        // 获取文件后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 拼接文件名及后缀
        String fileName = UUID.randomUUID().toString() + suffix;
        // 校验目录是否存在，若不存在，则创建
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 将临时文件转移至basePath进行保存
        try {
            file.transferTo(new File(basePath+ fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(fileName);
    }

}