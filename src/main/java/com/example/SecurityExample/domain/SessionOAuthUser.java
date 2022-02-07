package com.example.SecurityExample.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
public class SessionOAuthUser implements Serializable {
    private String name;

    private String email;

    private String picture;

    private String role;

    @Builder
    public SessionOAuthUser(OAuthUser user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.role = user.getRole();
    }
}
