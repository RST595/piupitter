package com.stpr.piupitter.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    public String uploadPath;
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/home").setViewName("home"); from example
//        registry.addViewController("/").setViewName("home"); from example
//        registry.addViewController("/hello").setViewName("hello"); from example
        registry.addViewController("/login").setViewName("logintmp"); //returning pages without logic
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file://" + uploadPath + "/"); //
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/"); //link where we are looking for resources(text color)
    }

}
