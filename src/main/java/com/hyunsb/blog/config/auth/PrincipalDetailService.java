package com.hyunsb.blog.config.auth;

import com.hyunsb.blog.model.User;
import com.hyunsb.blog.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 스프링이 로그인 요청을 가로챌 때 username, password 변수를 가로채는데
    // password 부분 처리는 알아서 한다.
    // username이 DB에 존재하는지 확인만 해주면 된다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        System.out.println("loadUserByUsername 메서드 호출");

        User principal = userRepository.findByUsername(username)
                .orElseThrow(()->{
                    return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. " + username);
                });

        System.out.println(principal.getUsername());
        System.out.println(principal.getPassword());

        return new PrincipalDetail(principal); // 시큐리티 세션에 유저 정보가 저장이 됨
    }

}
