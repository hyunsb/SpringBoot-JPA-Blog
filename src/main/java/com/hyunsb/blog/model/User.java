package com.hyunsb.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

// ORM: Object 를 테이블로 매핑해주는 기술
// @DynamicInsert // insert 시 null 인 필드를 제외시켜준다.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // User 클래스를 통하여 MySQL에 테이블 생성
public class User {

    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에 연결된 DB의 넘버링 전략을 따라간다.
    private int id; // 보통 long 타입으로 설정, 오라클: sequence, MySQL: auto_increment

    @Column(nullable = false, length = 30)
    private String username; // 아이디

    @Column(nullable = false, length = 100)
    private String password; // 패스워드

    @Column(nullable = false, length = 50)
    private String email; // 이메일

//    @ColumnDefault("'user'")
    @Enumerated(EnumType.STRING)
    private RoleType role;
//    private String role; // Enum을 쓰는 것이 좋다. (admin, user, manager 등의 권한을 지정)

    @CreationTimestamp // 시간 자동으로 입력
    private Timestamp createDate; // 가입날짜

}
