package com.pp.config;

import com.pp.utils.filter.HttpInterceptor;
import com.pp.utils.security.JwtAuthenticationTokenFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {
    
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(3600)
                .allowCredentials(true);
    }
    
//    @Override
//    public void addInterceptors(InterceptorRegistry interceptor) {
//        interceptor.addInterceptor(new HttpInterceptor()).addPathPatterns("/home/record","/home/onlineUser").excludePathPatterns("/home/login").excludePathPatterns("/home/register");
//    }
}
