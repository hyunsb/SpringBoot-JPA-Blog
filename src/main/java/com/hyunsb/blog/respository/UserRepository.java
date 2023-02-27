package com.hyunsb.blog.respository;

import com.hyunsb.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// JSP: DAO
// 자동으로 Bean 등록이 된다. @Repository 생략 가능
// <Object, Data Type of PK>
public interface UserRepository extends JpaRepository<User, Integer> {

    //JPA Naming 쿼리
    //SELECT * FROM user WHERE username = ?1 AND password = ?2
    Optional<User> findByUsernameAndPassword(String username, String password);

//    @Query(value = "value = SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//    User login(String username, String password);
}
