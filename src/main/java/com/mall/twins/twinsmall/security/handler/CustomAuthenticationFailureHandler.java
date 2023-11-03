package com.mall.twins.twinsmall.security.handler;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private DefaultRedirectStrategy defaultRedirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException{

        if(exception instanceof DisabledException){
            defaultRedirectStrategy.sendRedirect(request, response, "/login-disabled");
        }

        if(exception.getCause() instanceof LockedException){ //LockedException 예외가 발생하면 login-locked엔드포인트로 라다이렉트
            defaultRedirectStrategy.sendRedirect(request, response, "/login-locked");
            return;
        }

        defaultRedirectStrategy.sendRedirect(request, response, "/login-error");
    }

}
