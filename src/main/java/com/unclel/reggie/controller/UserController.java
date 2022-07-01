package com.unclel.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.unclel.reggie.common.R;
import com.unclel.reggie.entity.User;
import com.unclel.reggie.service.UserService;
import com.unclel.reggie.utils.SMSUtils;
import com.unclel.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ClassName UserController
 * @Description 响应手机端请求
 * @Author uncle_longgggggg
 * @Date 7/1/2022 1:41 PM
 * @Version 1.0
 */

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /*
    * @description:发送手机登录短信验证码
    * @param user
    * @param session
    * @return: * @return R<String>
    * @author: uncle_longgggggg
    * @time: 7/1/2022 2:32 PM
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession httpSession) {

        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)) {

            String validateCode = ValidateCodeUtils.generateValidateCode(4).toString();

            log.info(validateCode);
//            调用阿里云提供短信服务api发送短信
            SMSUtils.sendSms(phone, validateCode);

            httpSession.setAttribute(phone, validateCode);

            return R.success("短信验证码发送成功");
        }
        return  R.error("短信验证码发送失败");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession httpSession) {
        // 获取手机号
        String phone = map.get("phone").toString();
        // 获取验证码
        String validateCode = map.get("code").toString();
        // 获取session中验证码
        String sessionValidateCode = httpSession.getAttribute(phone).toString();
        // 进行验证码的比对
        if (sessionValidateCode != null &&validateCode.equals(sessionValidateCode)) {
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();

            userLambdaQueryWrapper.eq(User::getPhone, phone);

            User user = userService.getOne(userLambdaQueryWrapper);

            if (user == null) {
                user = new User();
                user.setPhone(phone);
                userService.save(user);
            }
            httpSession.setAttribute("user", user.getId());
            return R.success(user);
        }

        return R.error("登陆失败");
    }
}