package com.unclel.reggie.controller;

import com.unclel.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    /*
    * @description:文件下载
    * @param name
    * @param httpServletResponse
    * @return:
    * @author: uncle_longgggggg
    * @time: 6/30/2022 2:48 PM
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse httpServletResponse) {
        try {
            // 文件输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            // 输出流，通过输出流将文件写回浏览器，在浏览器展示图片
            ServletOutputStream outputStream = httpServletResponse.getOutputStream();
            httpServletResponse.setContentType("image/jpeg");
            // 输入输出对拷
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}