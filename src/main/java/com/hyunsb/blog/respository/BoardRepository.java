package com.hyunsb.blog.respository;

import com.hyunsb.blog.model.Board;
import com.hyunsb.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    List<Board> findByUser(User user);
}
