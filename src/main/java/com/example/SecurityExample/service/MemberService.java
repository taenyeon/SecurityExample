package com.example.SecurityExample.service;

import com.example.SecurityExample.domain.Member;
import com.example.SecurityExample.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        System.out.println("username = " + memberId);
       return memberRepository.findByMemberId(memberId).
                orElseThrow(() -> new UsernameNotFoundException(memberId));
    }

    public int joinMember(Member member){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        member.setMemberPwd(bCryptPasswordEncoder.encode(member.getPassword()));
        return memberRepository.joinMember(member);
    }
}
