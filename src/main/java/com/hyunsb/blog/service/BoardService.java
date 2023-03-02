package com.hyunsb.blog.service;

import com.hyunsb.blog.model.Board;
import com.hyunsb.blog.model.User;
import com.hyunsb.blog.respository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void save(Board board, User user){ // title, content
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    public Page<Board> findAllUserBoard(User user, Pageable pageable){
        return boardRepository.findByUser(user, pageable);
    }

    public Page<Board> findAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }
}
