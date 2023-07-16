package com.example.bossi.oauth2.UserInfo;

import java.util.Map;
import java.util.UUID;

public class GithubOAuth2UserInfo extends OAuth2UserInfo {

    public GithubOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("login");
    }

    @Override
    public String getEmail() {
        if(attributes.get("email") == null){
            String id = attributes.get("id") + UUID.randomUUID().toString();
            return id +"@github.com";
        }
        return (String) attributes.get("email");
    }

    @Override
    public String getLastName(){
        return (String) attributes.get("family_name");
    }

    @Override
    public String getPhoneNum() {
        return null;
    }

}
