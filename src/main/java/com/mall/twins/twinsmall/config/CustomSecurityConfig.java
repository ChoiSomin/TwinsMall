package com.mall.twins.twinsmall.config;

import com.mall.twins.twinsmall.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


import javax.sql.DataSource;

@Log4j2
@Configuration //스프링 애플리케이션의 구성을 자바 클래스로 명확하게 정의하고, 코드 기반의 구성을 할 수 있음
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true) //메소드 수준의 보안 설정을 활성화, 보안 규칙을 정의하고 적용
//@EnableGlobalMethodSecurity(prePostEnabled = true) = @PreAuthorize("hasRole('ROLE_ADMIN')") = @PostAuthorize("hasRole('ROLE_ADMIN')")
public class CustomSecurityConfig {
    //빈 정의
    /*
    * ex)
    * @Bean
    * public ~ (){
    *       ~~~~;
    * }
    * */

    private final DataSource dataSource;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){ //CustomeUserDetailsService가 정상적으로 작동하기 위함
        return new BCryptPasswordEncoder();

    }//passwordEncoder
    /*
     * PasswordEncoder 중 가장 무난한건 BCryptPasswordEncoder
     * BCryptPasswordEncoder : 해시 알고리즘으로 암호화 처리되는데 같은 문자열이라고 해도 매번 해시 처리된 결과가 다름
     * */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("==========configure==========");

        //커스텀 로그인 페이지
        http.formLogin().loginPage("/login"); //로그인화면에서 로그인을 진행한다는 설정
        //loginPage()를 지정하면 로그인이 필요한 경우 '/login'경로로 자동 리다이렉트 됨

        //CSRF 토큰 비활성화
        http.csrf().disable(); //csrf토큰 비활성화 username/password라는 파라미터만으로 로그인이 가능

        /* remember-me */
        http.rememberMe()
                .key("12345678")
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(userDetailsService)
                .tokenValiditySeconds(60*60*24*30);

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

    /* remember-me */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;

        //persistentTokenRepository : 쿠키의 값을 인코딩하기 위한 키값과 필요한 정보를 저장

    }//persistentTokenRepository


}
