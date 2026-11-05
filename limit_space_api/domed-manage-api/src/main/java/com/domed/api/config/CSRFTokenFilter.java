package com.domed.api.config;

import com.alibaba.nacos.common.utils.StringUtils;
import com.domed.core.exception.ApplicationException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @BelongsProject: limit_space_api
 * @BelongsPackage: com.domed.api.config
 * @Author: lirong
 * @CreateTime: 2024-11-21  19:14
 * @Description: TODO
 * @Version: 1.0
 */
public class CSRFTokenFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("CSRFTokenFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 执行一些预处理操作
        System.out.println("3Filter==Before the request...");
        // 获取header参数
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String headerValue = request.getHeader("X-CSRF-Token");
        System.out.println("headerValue=" + headerValue);
//        if (StringUtils.isEmpty(headerValue)) {
//            throw new ApplicationException("token 失效或者不合法。");
//        }
        // 调用下一个过滤器
        filterChain.doFilter(servletRequest, servletResponse);
        // 执行一些后处理操作
        System.out.println("3Filter==After the request...");
    }

    @Override
    public void destroy() {

    }
}
