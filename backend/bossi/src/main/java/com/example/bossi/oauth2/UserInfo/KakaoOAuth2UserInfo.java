package com.example.bossi.oauth2.UserInfo;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }
    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        if (properties == null) {
            return null;
        }

        return (String) properties.get("nickname");
    }

    @Override
    public String getEmail() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("kakao_account");

        if (properties == null) {
            return null;
        }
        return (String) properties.get("email");
    }


    @Override
    public String getLastName() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        if (properties == null) {
            return null;
        }

        String name = (String) properties.get("nickname");
        return name.substring(0,1);
    }

    @Override
    public String getPhoneNum() {
        return null;
    }

}
