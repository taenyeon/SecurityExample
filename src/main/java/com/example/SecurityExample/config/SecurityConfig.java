package com.example.SecurityExample.config;

import com.example.SecurityExample.service.MemberService;
import com.example.SecurityExample.service.MyOAuth2UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // UserDetailsService 의 구현체로 만들어진 memberService 를 사용하기위해 불러옴
    private  final MemberService memberService;
    private final MyOAuth2UserService myOAuth2UserService;

    public SecurityConfig(MemberService memberService, MyOAuth2UserService myOAuth2UserService) {
        this.memberService = memberService;
        this.myOAuth2UserService = myOAuth2UserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login","/googleLogin", "/join","/resources/**").permitAll()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/user/**").authenticated()
                // antMatchers 로 지정된 url 은 로그인(권한)이 없어도 페이지를 불러올 수 있다.
                .anyRequest().authenticated()
                // 이외의 url 은 권한이 있도록 강제하여 권한이 없을 경우, loginPage 로 보낸다.
                .and()
                .formLogin()
                // login 요청을 보낼 form을 설정한다.
                .usernameParameter("memberId")
                // 기존의 id 는 username 으로 설정되어 있으나, 이를 임의로 정한 memberId 로 사용하도록 함.
                .passwordParameter("memberPwd")
                // 기존의 pwd 는 password 으로 설정되어 있으나, 이를 임의로 정한 memberPwd 로 사용하도록 함.
                .loginPage("/login")
                // 기존의 Spring Security 에서 제공하는 로그인 페이지를 참고하여 새로운 로그인 페이지 생성.
                .defaultSuccessUrl("/")
                // 로그인 성공시, 불러올 페이지 -> 추후에는 실패시에 어떻게 처리하는지도 작성할 예정.
                .and()
                .logout()
                .logoutUrl("/logout")
                // 로그아웃을 실행할 url 설정
                .logoutSuccessUrl("/")
                // 로그아웃 성공시, 불러올 페이지
                .invalidateHttpSession(true)
                .and()
                .oauth2Login()
                .loginPage("/googleLogin")
                .userInfoEndpoint()
                .userService(myOAuth2UserService);
                // 로그아웃시, 세션 제거
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService)
                // 인증에 사용할 userDetailsService -> 구현체인 memberService 사용
                .passwordEncoder(new BCryptPasswordEncoder());
                // 비밀번호 설정에 사용한 Bcrypt 를 이용하여 비밀번호 확인하도록 함.
    }
}
