package com.hyunsb.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunsb.blog.config.auth.PrincipalDetail;
import com.hyunsb.blog.model.KakaoProfile;
import com.hyunsb.blog.model.OAuthToken;
import com.hyunsb.blog.model.User;
import com.hyunsb.blog.service.OAuthService;
import com.hyunsb.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

// 인증이 되지 않은 사용자들이 출입할 수 있는 경로 /auth/**
// uri = / 인 경우 index.html 허용
// static 이하의 /js/**, /css/**, /image/** 허용

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OAuthService oAuthService;

    @GetMapping("/auth/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm(@AuthenticationPrincipal PrincipalDetail principalDetail){

        return "user/updateForm";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) throws JsonProcessingException {
        oAuthService.kakaoUserInfoRequest(code);
        return "redirect:/";
    }
}
