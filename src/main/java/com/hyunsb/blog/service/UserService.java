package com.hyunsb.blog.service;

import com.hyunsb.blog.model.RoleType;
import com.hyunsb.blog.model.User;
import com.hyunsb.blog.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional
    public void join(User user) {
        String encPassword = encoder.encode(user.getPassword());
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);

        userRepository.save(user);
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
