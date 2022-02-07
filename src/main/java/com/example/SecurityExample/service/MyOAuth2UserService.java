package com.example.SecurityExample.service;

import com.example.SecurityExample.domain.OAuthAttributes;
import com.example.SecurityExample.domain.OAuthUser;
import com.example.SecurityExample.domain.SessionOAuthUser;
import com.example.SecurityExample.repository.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

@Service
public class MyOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final HttpSession session;
    private final MemberRepository memberRepository;

    public MyOAuth2UserService(HttpSession session, MemberRepository memberRepository) {
        this.session = session;
        this.memberRepository = memberRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest,OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(
                registrationId, userNameAttributeName, oAuth2User.getAttributes());

        OAuthUser user = saveOrUpdate(attributes);
        session.setAttribute("user",new SessionOAuthUser(user));
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRole()))
        ,attributes.getAttributes(),attributes.getNameAttributeKey());
    }

    private OAuthUser saveOrUpdate(OAuthAttributes attributes) {
        Optional<OAuthUser> user = memberRepository.findByEmail(attributes.getEmail());
        if(user.isPresent()){
            user.get().update(attributes.getName(),attributes.getPicture());
        }else {
            user = Optional.ofNullable(attributes.toDomain());
            memberRepository.saveOAuthUser(user.get());
        }
        return user.get();
    }

}
