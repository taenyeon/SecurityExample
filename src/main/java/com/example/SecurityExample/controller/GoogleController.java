package com.example.SecurityExample.controller;

import com.example.SecurityExample.domain.OAuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class GoogleController {
    private final HttpSession httpSession;

    @GetMapping("/google")
    public String index(Model model){

        OAuthUser user = (OAuthUser) httpSession.getAttribute("user");

        if(user != null){
            model.addAttribute("userName", user.getName());
        }
        return "/";
    }
    @GetMapping("/googleLogin")
    public String getGoogleForm(){
        return "googleEx";
    }
}