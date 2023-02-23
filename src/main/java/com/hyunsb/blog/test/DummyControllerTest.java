package com.hyunsb.blog.test;

import com.hyunsb.blog.model.User;
import com.hyunsb.blog.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyControllerTest {

    @Autowired // 의존성 주입 DI
    private UserRepository userRepository;

    @PostMapping("/dummy/join")
    public String join(User user){ // key: value
        System.out.println("userid: " + user.getId());
        System.out.println("username: " + user.getUsername());
        System.out.println("password: " + user.getPassword());
        System.out.println("email: " + user.getEmail());
        System.out.println("role: " + user.getRole());
        System.out.println("createDate: " + user.getCreateDate());

        userRepository.save(user);
        return "회원 가입이 완료 되었습니다.";
    }

}
