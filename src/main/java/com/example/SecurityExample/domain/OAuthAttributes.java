package com.example.SecurityExample.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId,String userNameAttributeName, Map<String,Object> attributes){
        return ofGoogle(userNameAttributeName,attributes);
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName,Map<String,Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public OAuthUser toDomain(){
        return OAuthUser.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role("ROLE_USER")
                .build();
    }
}
