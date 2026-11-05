package com.domed.api.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author LiRong
 * @Date 2024/7/17 17:43
 **/
@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<CSRFTokenFilter> csrfFilter() {
        FilterRegistrationBean<CSRFTokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CSRFTokenFilter());
        registrationBean.addUrlPatterns("*.json"); // 设置过滤器应用的URL模式
        registrationBean.setOrder(2); // 设置过滤器顺序
        return registrationBean;
    }
}
