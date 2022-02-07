package com.example.SecurityExample.repository;

import com.example.SecurityExample.domain.Member;
import com.example.SecurityExample.domain.OAuthUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberRepository {
    Optional<Member> findByMemberId(String memberId);
    int joinMember(Member member);
    Optional<OAuthUser> findByEmail(String email);
    int saveOAuthUser(OAuthUser user);
}
