package com.unclel.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.unclel.reggie.common.R;
import com.unclel.reggie.entity.Employee;
import com.unclel.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName EmployeeController
 * @Description TODO
 * @Author uncle_longgggggg
 * @Date 6/24/2022 10:24 AM
 * @Version 1.0
 */

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    /**
    * @description: 登录逻辑处理
    *  * @param request
 * @param employee
    * @return: com.unclel.reggie.common.R<com.unclel.reggie.entity.Employee>
    * @author: uncle_longgggggg
    * @time: 6/27/2022 9:16 PM
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){

        /*
          ○ 将页面提交的密码password进行md5加密处理
          ○ 根据页面提交的用户名username查询数据库
          ○ 如果没有查询到则返回登陆失败结果
          ○ 密码比对，如果不一致则返回登录失败结果
          ○ 查看员工状态，如果为已禁用状态，则返回员工已禁用结果
          ○ 登陆成功，将员工id存入Session并返回登陆成功结果
         */

//      将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
//      根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

//      如果没有查询到则返回登陆失败结果
        if (emp == null) {
            return R.error("登陆失败");
        }

//      密码比对，如果不一致则返回登录失败结果
        if (! emp.getPassword().equals(password)) {
            return R.error("密码错误，登陆失败");
        }

//      查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() == 0){
            return R.error("账号已禁用");
        }

//      登陆成功，将员工id存入Session并返回登陆成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);

    }

    /**
    * @description: 员工退出
    *  * @param httpServletRequest
    * @return: com.unclel.reggie.common.R<java.lang.String>
    * @author: uncle_longgggggg
    * @time: 6/27/2022 9:51 PM
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest httpServletRequest) {

//      清除session中该员工信息
        httpServletRequest.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
}