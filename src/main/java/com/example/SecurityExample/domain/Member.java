package com.example.SecurityExample.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter @Setter
public class Member implements UserDetails {
    // UserDetails 를 오버라이딩 하여 개발자가 임의로 user 구현
    private String memberId;
    // username 에 대응하는 필드로 아이디를 통해 로그인하고자 하는 user 정보 불러오기 가능
    private String memberPwd;

    private String role;
    // password 에 대응하는 필드로 Bcrypt 를 이용하여 암호화 처리

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한 정보의 getter 로 현재는 권한에 대해서 구현하지 않은 상태
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }
    @Override
    public String getPassword() {
        return memberPwd;
    }
    // password 의 getter 로 리턴 값을 memberPwd 로 설정

    @Override
    public String getUsername() {
        return memberId;
    }
    // username 의 getter 로 리턴 값을 memberId 로 설정
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // 계정 만료 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // 계정 잠김 여부
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // 비밀번호 만료 여부
    @Override
    public boolean isEnabled() {
        return true;
    }
    // 계정 활성화 여부
}
