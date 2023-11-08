package com.mall.twins.twinsmall.security.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class Custom403Handler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        log.info("Custom403Handler.handle() 403 에러 페이지 처리 ");

        response.setStatus(HttpStatus.FORBIDDEN.value());   // 403 에러 값 받음

        // JSON 요청이었는지 확인
        String contentType = request.getHeader("Content-type");
        boolean jsonRequest = contentType.startsWith("application/json");

        log.info("isJSON : " + jsonRequest);

        if(!jsonRequest) {
            response.sendRedirect("/member/login?error=ACCESS_DENIED");
        }

    }
}
