package com.mall.twins.twinsmall.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;

@Controller
@Log4j2
public class HeaderController {

    @GetMapping("/checkSession")
    public ResponseEntity<String> checkSession(Authentication authentication) {
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated();

        log.info("Session status: {}", isLoggedIn);

        return ResponseEntity.ok(isLoggedIn ? "true" : "false");
    }
}
