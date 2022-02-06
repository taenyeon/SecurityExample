package com.example.SecurityExample.controller;

import com.example.SecurityExample.domain.Member;
import com.example.SecurityExample.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    @GetMapping("/")
    public String main(){
        return "home";
    }

    @GetMapping("/join")
    public String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(Member member){
        memberService.joinMember(member);
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/user")
    public String isUser(){
        return "user";
    }
    @GetMapping("/admin")
    public String isAdmin(){
        return "admin";
    }
}
