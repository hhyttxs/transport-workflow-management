//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.domed.auth.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Order(Integer.MIN_VALUE)
@Configuration
public class CorsFilterConfig implements Filter {
    public CorsFilterConfig() {
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest)servletRequest;
            HttpServletResponse response = (HttpServletResponse)servletResponse;
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST,GET");
            response.setHeader("Access-Control-Allow-Headers", "*");
            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                response.setStatus(200);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }

        } catch (Throwable var6) {
            throw var6;
        }
    }

    public void destroy() {
    }
}
