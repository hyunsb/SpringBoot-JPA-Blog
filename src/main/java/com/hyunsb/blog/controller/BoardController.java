package com.hyunsb.blog.controller;

import com.hyunsb.blog.config.auth.PrincipalDetail;
import com.hyunsb.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping({"","/"})
    public String index(Model model){
//        @AuthenticationPrincipal PrincipalDetail principal  : parameter
//        System.out.println("로그인 사용자 아이디: " + principal.getUsername());

        model.addAttribute("boards", boardService.findAll());
        return "index"; // viewResolver 작동
    }

    @GetMapping("/board/saveForm")
    public String saveForm(){
        return "board/saveForm";
    }
}
