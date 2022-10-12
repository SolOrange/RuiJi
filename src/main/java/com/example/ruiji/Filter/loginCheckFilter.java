package com.example.ruiji.Filter;


import com.alibaba.fastjson.JSON;
import com.example.ruiji.Common.BaseContext;
import com.example.ruiji.Common.R;
import com.example.ruiji.pojo.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
@ComponentScan
public class loginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        Long id = Thread.currentThread().getId();
        log.info("线程ID2为：" + id.toString());
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取·请求URI
        String requestUri = request.getRequestURI();
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        //不需要处理的url
        if (check(urls, requestUri)) {
            filterChain.doFilter(request, response);
            return;
        }
        //用户已经登陆
        if (request.getSession().getAttribute("employee") != null) {
            Long empId = (Long) request.getSession().getAttribute("employee");
            //log.info("当前用户ID为：{}",empId);
            filterChain.doFilter(request, response);
            return;
        }

        if (request.getSession().getAttribute("user") != null) {
            Long userId = (Long) request.getSession().getAttribute("user");
            //log.info("当前用户ID为：{}",empId);
            filterChain.doFilter(request, response);
            return;
        }
        //未登录则返回登陆结果，通过输出流的数据向页面返回响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    //判断url是否是不需要处理的url
    public boolean check(String[] urls, String requestURI) {
        for (String uri : urls) {
            if (PATH_MATCHER.match(uri, requestURI)) {
                return true;
            }
        }
        return false;
    }
}
