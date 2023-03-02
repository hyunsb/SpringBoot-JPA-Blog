package com.hyunsb.blog.controller.api;

import com.hyunsb.blog.dto.ResponseDTO;
import com.hyunsb.blog.model.Board;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardApiController {

    @PostMapping("/api/board")
    public ResponseDTO<Integer> save(@RequestBody Board board){

        return new ResponseDTO<>(HttpStatus.OK.value(), 1);
    }
}
