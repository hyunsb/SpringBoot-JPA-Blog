package com.hyunsb.blog.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyunsb.blog.kakao.TokenParameter;
import com.hyunsb.blog.model.KakaoProfile;
import com.hyunsb.blog.model.OAuthToken;
import com.hyunsb.blog.model.RoleType;
import com.hyunsb.blog.model.User;
import com.hyunsb.blog.respository.UserRepository;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class UserService {

    @Value("${cos.key}")
    private String cosKey;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void join(User user) {
        validateDuplicateUsername(user.getUsername());
        String encPassword = encoder.encode(user.getPassword());
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);

        userRepository.save(user);
    }

    private void validateDuplicateUsername(String username){
        userRepository.findByUsername(username).ifPresent(e ->{
            throw new IllegalArgumentException("존재하는 회원 아이디 입니다.");
        });
    }

    @Transactional
    public void update(User requestUser) {
        User persistenceUser = userRepository.findById(requestUser.getId()).orElseThrow(() -> {
            throw new IllegalArgumentException("회원수정 실패: 회원 정보가 존재하지 않습니다.");
        });

        //validate 체크 리팩터링 필요
        if (persistenceUser.getOauth() == null || persistenceUser.getOauth().equals("")) {
            persistenceUser.setPassword(encoder.encode(requestUser.getPassword()));
            persistenceUser.setEmail(requestUser.getEmail());
        }
    }

    @Transactional(readOnly = true)
    public User findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->{
            throw new IllegalArgumentException("회원 정보가 존재하지 않습니다.");
        });
    }

    @Transactional(readOnly = true)
    public boolean isExistUser(String username){
        return userRepository.findByUsername(username)
                .orElseGet(User::new)
                .getUsername() != null;
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
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", TokenParameter.RequestParams.getGrantType());
        params.add("client_id", TokenParameter.RequestParams.getClientId());
        params.add("redirect_uri", TokenParameter.RequestParams.getRedirectUri());
        params.add("code", code);

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

        if(!isExistUser(kakaoUsername)) {
            User kakaoUser = User.builder()
                    .username(kakaoUsername)
                    .password(cosKey)
                    .email(kakaoProfile.getKakao_account().getEmail())
                    .oauth("kakao")
                    .build();

            join(kakaoUser);
        }

        System.out.println("=======================로그인 진행========================");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(kakaoUsername, cosKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

//    ============================ Spring Security 사용 이전의 로그인 로직 ===============================//

//    @Transactional(readOnly = true) // select 시 트랜잭션 시작, 서비스 종료 시 트랜잭션 종료 (정합성)
//    public User login(User user) {
//        Optional<User> result = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//
//        if(result.isPresent())
//            return result.get();
//
//        throw new IllegalStateException("회원 정보가 존재하지 않습니다.");
//    }
}
