package com.hyunsb.blog.controller;

import com.hyunsb.blog.config.auth.PrincipalDetail;
import com.hyunsb.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping({"","/"})
    public String index(Model model,
                        @PageableDefault(size = 4, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){

        model.addAttribute("boards", boardService.findAll(pageable));
        return "index"; // viewResolver 작동
    }

    @GetMapping("/board/myPost")
    public String myPost(Model model,
                         @AuthenticationPrincipal PrincipalDetail principalDetail,
                         @PageableDefault(size = 4, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){

        model.addAttribute("boards", boardService.findAllUserBoard(principalDetail.getUser(), pageable));
        return "board/myPost";
    }

    @GetMapping("/board/saveForm")
    public String saveForm(){
        return "board/saveForm";
    }

    @GetMapping("/board/{id}")
    public String boardDetail(@PathVariable int id, Model model){
        model.addAttribute("board", boardService.findBoardById(id));
        return "board/detail";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model){
        model.addAttribute("board", boardService.findBoardById(id));
        return "board/updateForm";
    }
}
