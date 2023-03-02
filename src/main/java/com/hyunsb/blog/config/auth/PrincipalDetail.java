package com.hyunsb.blog.config.auth;

import com.hyunsb.blog.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행
// 완료 시 UserDetails 타입의 오브젝트를 스프링 시큐리티의 고유 세션저장소에 저장
@Getter
public class PrincipalDetail implements UserDetails {
    private User user; // 컴포지션

    public PrincipalDetail(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정이 만료되지 않았는지를 리턴 (true: 만료되지 않음)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있지 않는지를 리턴 (true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는지를 리턴 (true: 만료되지 않음)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화(사용 가능) 인지를 리턴 (true: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 계정이 가지고 있는 권한 목록을 리턴한다. (권한이 여러개 있을 수 있어서 루프를 돌아야한다. 해당 프로젝트에서는 한개만)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collectors = new ArrayList<>();

        collectors.add(() -> {
            return "ROLE_" + user.getRole();
        });

        return collectors;
    }
}
