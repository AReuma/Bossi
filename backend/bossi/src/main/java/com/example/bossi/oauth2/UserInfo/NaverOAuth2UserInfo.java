package com.example.bossi.oauth2.UserInfo;

import java.util.HashMap;
import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);

        super.attributes = ((HashMap<String, Object>) attributes.get("response"));

        System.out.println(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("nickname");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getLastName(){
        return (String) attributes.get("family_name");
    }

    public String getPhoneNum() {
        return (String) attributes.get("mobile");
    }

}
