package com.hyunsb.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HttpControllerTest {

    private static final String TAG = "HttpControllerTest: ";


    // 인터넷 브라우저 요청은 무조건 get 요청밖에 할 수 없다.
    // http://localhost:8000/blog/http/get (select)
    @GetMapping("/http/get")
    public String getTest(Member member){ // id=1&username=ssar&password=1234&email=ssar@naver.com // MessageConverter
        return "get요청: " +
                member.getId() + " " +
                member.getUsername() + " " +
                member.getPassword() + " " +
                member.getEmail();
    }


    //======================lombok Test========================//

    @GetMapping("/http/lombokBuilder")
    public String lombokBuilderTest(){
        Member member = Member.builder().username("test").password("1234").email("email").build();
        System.out.println(TAG + "getter: " + member.getId());
        member.setId(1234);
        System.out.println(TAG + "setter: " + member.getId());

        return "lombok test complete";
    }

    @GetMapping("/http/lombok")
    public String lombokTest(){
        Member member = new Member(1, "test", "1234", "email");
        System.out.println(TAG + "getter: " + member.getId());
        member.setId(1234);
        System.out.println(TAG + "setter: " + member.getId());

        return "lombok test complete";
    }

}
