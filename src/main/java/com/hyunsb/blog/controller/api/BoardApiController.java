package com.hyunsb.blog.controller.api;

import com.hyunsb.blog.config.auth.PrincipalDetail;
import com.hyunsb.blog.dto.ResponseDTO;
import com.hyunsb.blog.model.Board;
import com.hyunsb.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDTO<Integer> save(@RequestBody Board board,
                                     @AuthenticationPrincipal PrincipalDetail principalDetail){

        boardService.save(board, principalDetail.getUser());
        return new ResponseDTO<>(HttpStatus.OK.value(), 1);
    }

    @DeleteMapping("/api/board/{id}")
    public ResponseDTO<Integer> delete(@PathVariable int id){
        boardService.boardDelete(id);
        return new ResponseDTO<>(HttpStatus.OK.value(), 1);
    }
}
