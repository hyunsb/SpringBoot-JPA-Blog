package com.hyunsb.blog.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
