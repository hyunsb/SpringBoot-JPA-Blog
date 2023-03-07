package com.hyunsb.blog.controller.api;

import com.hyunsb.blog.config.auth.PrincipalDetail;
import com.hyunsb.blog.dto.ResponseDTO;
import com.hyunsb.blog.model.RoleType;
import com.hyunsb.blog.model.User;
import com.hyunsb.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/auth/joinProc")
    public ResponseDTO<Integer> save(@RequestBody User user){ //username, password, email

        userService.join(user);
        // 오류 시 ExceptionHandler 에서 처리

        return new ResponseDTO<Integer>(HttpStatus.OK.value(), 1);
    }

    // @RequestBody 사용 시 json 타입을 받을 수 있음
    // 사용하지 않을 시 (key=value 형태의 데이터 : x-www-form-urlencoded)
    @PutMapping("/user")
    public ResponseDTO<Integer> update(@RequestBody User user){
        userService.update(user);
        // 서비스가 종료되면서 트랜잭션도 종료되어 DB의 값은 변경되지만
        // 시큐리티 세션의 정보는 변경 되지 않은 상태이기 때문에 직접 세션의 값 변경이 필요하다.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

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
