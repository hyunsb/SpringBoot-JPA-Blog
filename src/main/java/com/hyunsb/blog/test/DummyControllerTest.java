package com.hyunsb.blog.test;

import com.hyunsb.blog.model.RoleType;
import com.hyunsb.blog.model.User;
import com.hyunsb.blog.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RestController // html 파일이 아닌 data 를 리턴해주는 controller
public class DummyControllerTest {

    @Autowired // 의존성 주입 DI
    private UserRepository userRepository;

    //======================= delete test =============================//
    @DeleteMapping("/dummy/user/{id}")
    public String deleteUser(@PathVariable int id){
//        userRepository.findById(id).orElseThrow(()->{
//            throw new IllegalArgumentException("삭제에 실패 했습니다.");
//        });

        try{
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            return "삭제에 실패 했습니다.";
        }

        return "삭제 테스트 완료";
    }

    //======================= update test =============================//

    // DataJpa의 save함수는
    // id를 전달하지 않으면 insert
    // id를 전달한 경우 해당 id에 대한 데이터가 있다면 update
    // id를 전달한 경우 해당 id에 대한 데이터가 없다면 insert
    @Transactional // 함수 종료 시 자동 commit 수행
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id,
                           @RequestBody User requestUser){
        // @RequestBody
        // json 데이터를 java Object 로 자동 변환 (MessageConverter 의 Jackson 라이브러리)

        User updateUserInfo = userRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("수정에 실패하였습니다.");
        });

        updateUserInfo.setPassword(requestUser.getPassword());
        updateUserInfo.setEmail(requestUser.getEmail());

//        userRepository.save(updateUserInfo);
        //@Transactional 어노테이션 사용
        //더티 체킹
        //영속화된 데이터와 DB의 데이터를 비교하여 다르다면 commit 시 DB 데이터 변경
        return updateUserInfo;
    }

    //======================= select test =============================//

    @GetMapping("/dummy/users")
    public List<User> list(){
        return userRepository.findAll();
    }

    // http://localhost:8000/blog/dummy/user?page=1
    // 한페이지당 2건의 데이터를 리턴 (page는 0부터 시작), key id를 기준으로 내림차순 정렬
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC)
                               Pageable pageable){

        Page<User> pagingUser = userRepository.findAll(pageable);
        return pagingUser.getContent();
    }

    // {id} 주소로 파라매터를 전달 받을 수 있다.
    // http://localhost:8000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) throws IllegalArgumentException{

        // 람다식
//        return userRepository.findById(userId).orElseThrow(() -> {
//            return new IllegalArgumentException("회원 정보가 존재하지 않습니다. id: " + userId);
//        });

        // MessageConverter 가 응답시에 User 객체를 Json 타입으로 자동변환 해준다.
        return userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("회원정보가 존재하지 않습니다. id: " + id);
            }
        });

    }

    //======================= insert test =============================//
    @PostMapping("/dummy/join")
    public String join(User user){ // key: value
        System.out.println("userid: " + user.getId());
        System.out.println("username: " + user.getUsername());
        System.out.println("password: " + user.getPassword());
        System.out.println("email: " + user.getEmail());
        System.out.println("role: " + user.getRole());
        System.out.println("createDate: " + user.getCreateDate());

        user.setRole(RoleType.USER);

        userRepository.save(user);
        return "회원 가입이 완료 되었습니다.";
    }

}
