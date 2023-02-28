package com.hyunsb.blog.controller.api;

import com.hyunsb.blog.dto.ResponseDTO;
import com.hyunsb.blog.model.RoleType;
import com.hyunsb.blog.model.User;
import com.hyunsb.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @PostMapping("/api/user")
    public ResponseDTO<Integer> save(@RequestBody User user){ //username, password, email
        System.out.println("UserApiController: save 메서드 호출");

        // DB에 insert
        user.setRole(RoleType.USER);
        userService.join(user);

        return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
    }

    @PostMapping("/api/user/login")
    private ResponseDTO<Integer> login(@RequestBody User user){
        System.out.println("UserApiController: login 메서드 호출");

        User principal = userService.login(user);
        session.setAttribute("principal", principal);

        return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
    }

    // Spring Security 를 활용한 로그인

}
