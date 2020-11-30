package com.cao.youth.configuration;

import com.cao.youth.core.interceptors.PermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 曹学习
 * @description InterceptorConfiguration
 * @date 2020/8/28 12:19
 */
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    @Bean
    public HandlerInterceptor getPermissionInterceptor(){
        return new PermissionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.getPermissionInterceptor());
    }
}
