package com.example.bossi.config.auth;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다 .
// 로그인 진행이 완료가 되면 싴리티 session을 만들어줌 (Security ContextHolder)

// Security Session 에 세션을 저장하는데 Authentication 객체가 들어간다
// Authentication 객체안에는 UserDetails 객체가 존재

// Security Session -> Authentication -> UserDetails(PrincipalDetails)

//UserDetails 를 꺼내면 User 오브젝트에 접근이 가능해짐

import com.example.bossi.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Random;

public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user;

    private Map<String, Object> attributes;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 해당 User의 권한을 리턴하는 곳. 
        Collection<GrantedAuthority> collect = new ArrayList<>();

        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole().name();
            }
        });

        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public String getRole(){
        return user.getRole().getKey();
    }

    public boolean getRegisterStatus() {
        return user.getRegisterStatus();
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정 만료 되었니?
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 1년동안 회원이 로그인을 안하는 경우 유령 회원으로 만들기.

        return true;
    }

    @Override
    public String getName() {
        return user.getNickName();
    }
}
