package com.hyunsb.blog.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunsb.blog.kakao.KakaoTokenParams;
import com.hyunsb.blog.kakao.KakaoUserInfoHeaders;
import com.hyunsb.blog.model.KakaoProfile;
import com.hyunsb.blog.model.OAuthToken;
import com.hyunsb.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class OAuthService {

    @Value("${cos.key}")
    private String cosKey;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    public OAuthToken kakaoTokenRequest(String code) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(kakaoTokenParamsInit(code), headers);

        // POST 방식으로 HTTP 요청, response 변수에 응답을 받음
        ResponseEntity<String> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.getBody(), OAuthToken.class);
    }

    private MultiValueMap<String, String> kakaoTokenParamsInit(String code){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        for(KakaoTokenParams tokenParam : KakaoTokenParams.values())
            params.add(tokenParam.getParamName(), tokenParam.getParamValue());
        params.add("code", code);
        return params;
    }


    public void kakaoUserInfoRequest(String code) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoTokenRequest(code).access_token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader 와 HttpBody 를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers);

        // POST 방식으로 HTTP 요청, response 변수에 응답을 받음
        ResponseEntity<String> response2 = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );

        // Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = objectMapper.readValue(response2.getBody(), KakaoProfile.class);

        String kakaoUsername = kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId();

        if(!userService.isExistUser(kakaoUsername)) {
            User kakaoUser = User.builder()
                    .username(kakaoUsername)
                    .password(cosKey)
                    .email(kakaoProfile.getKakao_account().getEmail())
                    .oauth("kakao")
                    .build();

            userService.join(kakaoUser);
        }

        System.out.println("=======================로그인 진행========================");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(kakaoUsername, cosKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }



    public void oAuthLogin(String code) throws JsonProcessingException {

        //POST 방식으로 key=value 데이터를 요청 (kakao 에게)
        // Retrofit2 라이브러리
        // OkHttp
        // RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = kakaoTokenParamsInit(code);

        // HttpHeader 와 HttpBody 를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);

        // POST 방식으로 HTTP 요청, response 변수에 응답을 받음
        ResponseEntity<String> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);


        //==========================카카오 사용자 정보 요청============================//
        RestTemplate restTemplate2 = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.access_token);
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader 와 HttpBody 를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers2);

        // POST 방식으로 HTTP 요청, response 변수에 응답을 받음
        ResponseEntity<String> response2 = restTemplate2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2,
                String.class
        );

        // Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = objectMapper.readValue(response2.getBody(), KakaoProfile.class);

        String kakaoUsername = kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId();

        if(!userService.isExistUser(kakaoUsername)) {
            User kakaoUser = User.builder()
                    .username(kakaoUsername)
                    .password(cosKey)
                    .email(kakaoProfile.getKakao_account().getEmail())
                    .oauth("kakao")
                    .build();

            userService.join(kakaoUser);
        }

        System.out.println("=======================로그인 진행========================");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(kakaoUsername, cosKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
