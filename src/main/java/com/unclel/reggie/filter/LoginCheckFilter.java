package com.unclel.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.unclel.reggie.common.BaseContext;
import com.unclel.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * @ClassName LoginCheckFilter
 * @Description 用于拦截非登录用户
 * @Author uncle_longgggggg
 * @Date 6/28/2022 10:25 AM
 * @Version 1.0
 */

@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 获取本次请求的URI
        String requestURI = request.getRequestURI();

        // 定义不需要进行未登录拦截的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "employee/logout",
                "/backend/**",
                "front/**"
        };

        // 判断本次请求是否需要处理
        boolean checkNotLoginUrl = checkNotLoginUrl(urls, requestURI);

        // 如果不需要处理，直接放行
        if (checkNotLoginUrl) {
            log.info("本次请求{}不需要处理", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        // 判断登陆状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("employee") != null) {
            log.info("id为：{}的用户已登录", request.getSession().getAttribute("employee"));
            // 通过登录过滤器获得session并通过ThreadLocal在属于此线程的多个方法中进行传递
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request, response);
            return;
        }

        // 如果未登录，则返回未登录结果
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }


    /*
    * @description:检查当前登录情况
    * @param urls
    * @param requestURI
    * @return: * @return boolean
    * @author: uncle_longgggggg
    * @time: 6/28/2022 4:18 PM
     */
    public boolean checkNotLoginUrl (String[] urls, String requestURI){
        for (String url : urls) {
            boolean matchResult = antPathMatcher.match(url, requestURI);
            if (matchResult) {
                return true;
            }

        }
        return false;
    }
}