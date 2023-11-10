package com.mall.twins.twinsmall.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${uploadPath}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/productImages/**")
                .addResourceLocations(uploadPath);
    }

    @Bean
    public FormattingConversionService conversionService() {
        FormattingConversionService formattingConversionService = new FormattingConversionService();
        DateFormatterRegistrar registrar = new DateFormatterRegistrar();
        registrar.setFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
        registrar.registerFormatters(formattingConversionService);
        return formattingConversionService;
    }

}
