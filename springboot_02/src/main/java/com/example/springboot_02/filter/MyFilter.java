package com.example.springboot_02.filter;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MyFilter  implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("MyFliter.init()....");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        StringBuffer requestURL = request.getRequestURL();
        System.out.println("This is my fliter:"+requestURL);

        filterChain.doFilter(servletRequest,servletResponse);

    }

    @Override
    public void destroy() {
        System.out.println("MyFilter.destory()....");

    }
}
