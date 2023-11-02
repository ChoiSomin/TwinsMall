package com.mall.twins.twinsmall.config;

import com.mall.twins.twinsmall.security.CustomUserDetailsService;
import com.mall.twins.twinsmall.security.handler.Custom403Handler;
import com.mall.twins.twinsmall.security.handler.CustomAuthenticationFailureHandler;
import com.mall.twins.twinsmall.security.handler.CustomSocialLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Log4j2
@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private final CustomUserDetailsService userDetailService;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }//passwordEncoder

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        /* filterchain

        * -작동하면 list에 바로 접근 가능
        * -모든 사용자가 모든 경로에 접근할 수 있음
        *
        * -내부 코드를 이용해 최소한의 설정으로 필요한 자원의 접근을 제어
        * */

        log.info("===============configure=============="); //동작 여부 확인

        /*커스텀 로그인 페이지 */
        http.formLogin()
                .loginPage("/member/login")      // Form 로그인 기능 작동, 커스텀 로그인 페이지
                .defaultSuccessUrl("/index")             // 로그인 성공시 index페이지 이동
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("member/logout")) // 로그아웃 URL
                .logoutSuccessUrl("/index");              // 로그아웃 성공시 이동할 URL //로그인이 필요한 경우 로그인 페이지로 리다이렉트

        /* CSRF토큰 비활성화 */
        http.csrf().disable();

        /* 쿠키를 이용해서 로그인 정보 유지, (persistent_logins) 테이블 이용 */
        http.rememberMe()
                .key("12345678")
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(userDetailService)
                .tokenValiditySeconds(60*60*24*30);

        /* 403 에러 발생시 예외 처리로 로그인 페이지로 이동하고 파라미터에 error=ACCESS_DENIED 값 전달 */
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler()); //403

        /* OAuth2를 사용한 소셜 로그인 */
        http.oauth2Login()
                .loginPage("/member/login")
                .successHandler(authenticationSuccessHandler());
        return http.build();
    }//filterChain

    /* Custom403Handler 클래스 생성자 빈 처리 */
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new Custom403Handler();
    }

    /* css나 js 파일 등 정적 자원에 시큐리티 필터 적용 해제 */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){ //정적 자원들은 스프링 시큐리티 적용에서 제외시킬 수 있음

        log.info("===============web configure===========");

        return (web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
    }//webSecurityCustomizer

    /* remember-me 쿠키의 값을 인코딩하기 위한 키와 정보를 저장할 때 사용 */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }

    /* 로그인 Locked */
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.requiresChannel()
                .anyRequest()
                .requiresSecure()
                .and()
                .authorizeRequests()
                .antMatchers("/adduser", "/login", "login-error", "login-verified", "/login-disabled", "/verify/email", "/login-locked").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").failureHandler(customAuthenticationFailureHandler);
    }//configure

    /* 시큐리티 설정에 소셜 로그인 성공 시 페이지 이동 핸들러 추가 */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {

        return new CustomSocialLoginSuccessHandler(passwordEncoder());
    }

}
