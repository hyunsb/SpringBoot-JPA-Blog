package com.hyunsb.blog.config;

import com.hyunsb.blog.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Bean
    public BCryptPasswordEncoder encodePWD(){
        return new BCryptPasswordEncoder();
    }

    // 시큐리티가 대신 로그인을 한다. 이때 password 를 가로채는데
    // 해당 password 가 무엇으로 해쉬가 되었는지 알아야
    // 같은 해쉬로 암호화하여 DB에 존재하는 해쉬와 비교할 수 있음
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable() // csrf 토큰 비활성화 (테스트시 걸어두는 게 좋음)

                .authorizeRequests()
                    .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()

                .and()
                    .formLogin()
                    .loginPage("/auth/loginForm")
                    .loginProcessingUrl("/auth/loginProc")  // 스프링 시큐리티가 해당 주소로 들어오는 요청을 가로채서 대신 로그인
                    .defaultSuccessUrl("/");

        return http.build();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf().disable() //csrf 토큰 비활성화 (테스트시 걸어두는게 좋음)
//
//                .authorizeRequests()
//                .antMatchers("/","/auth/**","/js/**","/css/**", "/images/**").permitAll()
//                .anyRequest()
//                .authenticated()
//
//                .and()
//                .formLogin()
//                .loginPage("/auth/loginForm")
//                .loginProcessingUrl("/auth/loginProc") //스프링 시큐리티가 로그인을 가로챈다.
//                .defaultSuccessUrl("/"); //정상적으로 요청이 완료
//        return http.build();
//    }
}
