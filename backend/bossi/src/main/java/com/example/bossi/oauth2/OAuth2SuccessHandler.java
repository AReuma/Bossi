package com.example.bossi.oauth2;

import com.example.bossi.config.auth.PrincipalDetails;
import com.example.bossi.entity.Role;
import com.example.bossi.entity.User;
import com.example.bossi.repository.user.UserRepository;
import com.example.bossi.service.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final JwtTokenService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String targetUrl;

        System.out.println(authentication.getPrincipal().toString());

        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();

        if(Objects.equals(user.getRole(), Role.USER.getKey())){
            targetUrl = "http://localhost:8080/oauth2/redirect";
        }else {
            // 사장님이면 본인페이지로 이동하도록 변경해두자.
            targetUrl = "http://localhost:8080/oauth2/redirect";
        }

        User userEntity = userRepository.findUserByEmail(user.getUsername());

        String access_token = jwtService.createAccessToken(userEntity.getEmail(), userEntity.getNickName(), userEntity.getRole().getKey(), user.getRegisterStatus());

        String refresh_token = jwtService.createRefreshToken();

        System.out.println(access_token);

        //jwtService.updateRefreshToken(userEntity.getEmail(), refresh_token);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("access_token", access_token)
                .queryParam("refresh_token", refresh_token)
                .build().toUriString();
    }


}
