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

        model.addAttribute("boards", boardService.findAll());
        return "index"; // viewResolver 작동
    }

    @GetMapping("/board/myPost")
    public String myPost(Model model,
                         @AuthenticationPrincipal PrincipalDetail principalDetail){

        model.addAttribute("boards", boardService.findAllUserBoard(principalDetail.getUser()));
        return "board/myPost";
    }

    @GetMapping("/board/saveForm")
    public String saveForm(){
        return "board/saveForm";
    }
}
