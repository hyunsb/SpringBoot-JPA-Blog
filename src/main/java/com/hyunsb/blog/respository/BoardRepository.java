package com.hyunsb.blog.respository;

import com.hyunsb.blog.model.Board;
import com.hyunsb.blog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    Page<Board> findByUser(User user, Pageable pageable);
}
