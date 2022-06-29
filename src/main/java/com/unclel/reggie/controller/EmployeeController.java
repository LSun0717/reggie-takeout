package com.unclel.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.unclel.reggie.common.R;
import com.unclel.reggie.entity.Employee;
import com.unclel.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

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

    /*
    * @description:新增员工
    * @param employee
    * @return: * @return R<String>
    * @author: uncle_longgggggg
    * @time: 6/28/2022 4:38 PM
     */
    @PostMapping
    public R<String> save(HttpServletRequest httpServletRequest, @RequestBody Employee employee) {
        log.info("新增员工， 员工信息：{}", employee.toString());

        // 设置经md5加密过后的123456为初始密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8)));

        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        // 从session中获取当前登录用户信息
        Long empId = (Long) httpServletRequest.getSession().getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);

        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    /*
    * @description:分页查询
    * @param page
    * @param pageSize
    * @param name
    * @return: * @return R<Page>
    * @author: uncle_longgggggg
    * @time: 6/28/2022 5:44 PM
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {

        log.info("page:{}, pageSzie, name:{}", page, pageSize, name);
        // 构造分页构造器
        Page pageInfo = new Page(page, pageSize);
        // 构造条件构造器
        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件
        employeeLambdaQueryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        // 添加排序条件
        employeeLambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);
        // 执行查询
        employeeService.page(pageInfo, employeeLambdaQueryWrapper);

        return R.success(pageInfo);
    }

    /*
    * @description:根据id修改员工信息
    * @param employee
    * @return: * @return R<String>
    * @author: uncle_longgggggg
    * @time: 6/29/2022 10:24 AM
     */
    @PutMapping()
    public R<String> update(HttpServletRequest httpServletRequest, @RequestBody Employee employee) {
        Long empId = (Long) httpServletRequest.getSession().getAttribute("employee");
        log.info("修改id为：{}员工的信息", empId.toString());
        // 设置更新时间以及执行更新的人
        employee.setUpdateUser(empId);
        employee.setUpdateTime(LocalDateTime.now());

        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    /*
    * @description:根据id查询员工信息
    * @param id
    * @return: * @return R<Employee>
    * @author: uncle_longgggggg
    * @time: 6/29/2022 11:22 AM
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("根据id查询员工信息");
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("没有查询到对应员工信息");
    }
}