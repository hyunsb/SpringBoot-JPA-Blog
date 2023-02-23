package com.hyunsb.blog.respository;

import com.hyunsb.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JSP: DAO
// 자동으로 Bean 등록이 된다. @Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer> { // <Object, Data Type of PK>

}
