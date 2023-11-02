package com.mall.twins.twinsmall.config;


import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    /*@Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<Item, ItemFormDto>() {
            protected void configure() {
                map().setItemNm(source.getTitle());
                map().setItemDetail(source.getDetail());
                // 다른 규칙들...
            }
        });

        return modelMapper;
    }*/
}