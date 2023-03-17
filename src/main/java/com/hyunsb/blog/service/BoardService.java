package com.hyunsb.blog.service;

import com.hyunsb.blog.model.Board;
import com.hyunsb.blog.model.Reply;
import com.hyunsb.blog.model.User;
import com.hyunsb.blog.respository.BoardRepository;
import com.hyunsb.blog.respository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public void save(Board board, User user){ // title, content
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> findAllUserBoard(User user, Pageable pageable){
        return boardRepository.findByUser(user, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Board> findAll(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board findBoardById(int id) {
        return boardRepository.findById(id).
                orElseThrow(()->{
                    throw new IllegalArgumentException("게시글 정보가 존재하지 않습니다.");
                });
    }

    @Transactional
    public void boardDelete(int id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void updateBoard(int id, Board requestBoard) {
        // 수정 전 영속화
        Board persistenceBoard = boardRepository.findById(id).orElseThrow(()->{
            throw new IllegalArgumentException("글 찾기 실패: 게시글이 존재하지 않습니다.");
        }); // 영속성 컨텍스트에 board 가 추가됨

        persistenceBoard.setTitle(requestBoard.getTitle());
        persistenceBoard.setContent(requestBoard.getContent());
        // 해당 함수 종료 시(Service 가 종료될 때) 트랜잭션이 종료되면서 더티체킹 진행 -> 자동 업데이트(DB flush)
    }

    @Transactional
    public void replySave(User user, int boardId, Reply requestReply) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> {
            throw new IllegalArgumentException("댓글 등록 실패: 게시글 id를 찾을 수 없습니다.");
        });
        requestReply.setUser(user);
        requestReply.setBoard(board);

        replyRepository.save(requestReply);
    }

    public void replyDelete(int replyId) {
        replyRepository.deleteById(replyId);
    }
}
