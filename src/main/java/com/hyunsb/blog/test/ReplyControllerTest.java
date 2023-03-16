package com.hyunsb.blog.test;

import com.hyunsb.blog.model.Board;
import com.hyunsb.blog.respository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReplyControllerTest {

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("/reply/board/{id}")
    public Board replyTest(@PathVariable int id){
        return boardRepository.findById(id).get();
    }
}
