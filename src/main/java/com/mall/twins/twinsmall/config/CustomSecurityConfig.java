package com.mall.twins.twinsmall.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Log4j2
@Configuration //스프링 애플리케이션의 구성을 자바 클래스로 명확하게 정의하고, 코드 기반의 구성을 할 수 있음
@RequiredArgsConstructor
public class CustomSecurityConfig {
    //빈 정의
    /*
    * ex)
    * @Bean
    * public ~ (){
    *       ~~~~;
    * }
    * */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("==========configure==========");

        http.formLogin(); //로그인화면에서 로그인을 진행한다는 설정

        return http.build();
    }//filterChain

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        log.info("======web configure======");

        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }//webSecurityCustomizer
    /* webSecurityCustomizer Method
    * configure를 앞의 코드와 같이 설정하면 정적 자원들은 스프링 시쿠리티 적용에서 제외시킬 수 있음.
    * ex) CSS를 호출하면 필터가 동작하지 않음
    * */
}
