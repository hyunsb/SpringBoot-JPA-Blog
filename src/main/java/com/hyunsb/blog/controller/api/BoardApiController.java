package com.hyunsb.blog.controller.api;

import com.hyunsb.blog.config.auth.PrincipalDetail;
import com.hyunsb.blog.dto.ResponseDTO;
import com.hyunsb.blog.model.Board;
import com.hyunsb.blog.model.Reply;
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

    @PutMapping("/api/board/{id}")
    public ResponseDTO<Integer> update(@PathVariable int id,
                                       @RequestBody Board board){

        boardService.updateBoard(id, board);
        return new ResponseDTO<>(HttpStatus.OK.value(), 1);
    }

    @PostMapping("/api/board/{id}/reply")
    public ResponseDTO<Integer> replySave(@PathVariable int id,
                                          @RequestBody Reply reply,
                                          @AuthenticationPrincipal PrincipalDetail principalDetail){

        boardService.replySave(principalDetail.getUser(), id, reply);
        return new ResponseDTO<>(HttpStatus.OK.value(), 1);
    }

//    /api/board/${boardId}/${replyId}
    @DeleteMapping("/api/board/{boardId}/{replyId}")
    public ResponseDTO<Integer> replyDelete(@PathVariable int boardId,
                                            @PathVariable int replyId){

        boardService.replyDelete(replyId);
        return new ResponseDTO<>(HttpStatus.OK.value(), 1);
    }

}
