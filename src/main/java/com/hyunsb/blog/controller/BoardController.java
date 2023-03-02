package com.hyunsb.blog.controller;

import com.hyunsb.blog.config.auth.PrincipalDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping({"","/"})
    public String index(){
//        @AuthenticationPrincipal PrincipalDetail principal  : parameter
//        System.out.println("로그인 사용자 아이디: " + principal.getUsername());
        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm(){
        return "board/saveForm";
    }
}
