package com.mall.twins.twinsmall.config;


import com.mall.twins.twinsmall.dto.ItemFormDto;
import com.mall.twins.twinsmall.entity.Item;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
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