package com.hyunsb.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor // 모든 생성자
@NoArgsConstructor // 빈생성자
public class Member {

    private int id;
    private String username;
    private String password;
    private String email;

    @Builder //  빌더패턴
    public Member(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
