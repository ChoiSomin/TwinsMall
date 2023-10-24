package com.mall.twins.twinsmall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TwinsMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwinsMallApplication.class, args);
    }

}
