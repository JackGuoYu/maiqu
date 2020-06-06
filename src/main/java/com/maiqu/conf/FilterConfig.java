package com.maiqu.conf;

import com.maiqu.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.Filter;

@Configuration
public class FilterConfig {

    @Bean   //相当于spring中<bean>标签
    public FilterRegistrationBean<LoginFilter> testFilterRegistration() {
        FilterRegistrationBean<LoginFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(loginFilter());
        registration.addUrlPatterns("/admin/*");//配置过滤路径
        registration.setName("loginFilter");//设置filter名称
        registration.setOrder(1);//请求中过滤器执行的先后顺序，值越小越先执行
        return registration;
    }

    @Bean
    public LoginFilter loginFilter() {
        return new LoginFilter();
    }
}
