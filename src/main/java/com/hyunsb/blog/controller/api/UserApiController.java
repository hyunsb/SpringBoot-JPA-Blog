package com.hyunsb.blog.controller.api;

import com.hyunsb.blog.dto.ResponseDTO;
import com.hyunsb.blog.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    @PostMapping("/api/user")
    public ResponseDTO<Integer> save(@RequestBody User user){
        System.out.println("UserApiController save메서드 호출");
        return new ResponseDTO<Integer>(HttpStatus.OK, 1);
    }
}
