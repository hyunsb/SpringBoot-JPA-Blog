package com.hyunsb.blog.controller.api;

import com.hyunsb.blog.dto.ResponseDTO;
import com.hyunsb.blog.model.RoleType;
import com.hyunsb.blog.model.User;
import com.hyunsb.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/auth/joinProc")
    public ResponseDTO<Integer> save(@RequestBody User user){ //username, password, email

        userService.join(user);
        // 오류 시 ExceptionHandler 에서 처리

        return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
    }


//   ============================ Spring Security 사용 이전의 로그인 컨트롤러 ===============================//

//    @PostMapping("/api/user/login")
//    private ResponseDTO<Integer> login(@RequestBody User user, HttpSession session){
//        System.out.println("UserApiController: login 메서드 호출");
//
//        User principal = userService.login(user);
//        session.setAttribute("principal", principal);
//
//        return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
//    }

    // Spring Security 를 활용한 로그인


}
