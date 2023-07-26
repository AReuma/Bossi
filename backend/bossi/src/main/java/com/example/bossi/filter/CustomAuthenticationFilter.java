package com.example.bossi.filter;

import com.example.bossi.config.auth.PrincipalDetails;
import com.example.bossi.entity.User;
import com.example.bossi.service.jwt.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    /*@Value("${jwt.token.secretKey}")
    private String secretKey;*/

    // username /password 로 로그인을 하려고 하는지 체크하여 승인이 되면 Authentication을 부여하고 이동할 페이지로 이동.

    // Security Session -> Authentication -> UserDetails(PrincipalDetails)
    // Authentication을 만들고 인증을 처리하는 인터페이스가 AuthenticationManager

    private final AuthenticationManager authenticationManager;
//    public CustomAuthenticationFilter(AuthenticationManager authenticationManager){
//        this.authenticationManager = authenticationManager;
//    }

    public CustomAuthenticationFilter(AuthenticationManager authentication){
        this.authenticationManager = authentication;

        setFilterProcessesUrl("/api/v1/users/login");
    }


    // AuthenticationManager 는 authenticate() 메소드만 가짐.
    // authentication 은 유저 id, password 등의 인증정보가 있음.

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("필터 순서 1");
        log.info("attemptAuthentication: {}", request);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User user = objectMapper.readValue(request.getInputStream(), User.class);

            //log.info("email: {}", user.getEmail());

            // 입력된 값이 존재하는지 확인
            if(user.getEmail().isEmpty() || user.getPassword().isEmpty()){
                throw new IllegalArgumentException("Email or Password is Empty");
            }

            String email = user.getEmail();
            String password = user.getPassword();

            log.info("id : {}, pw : {}", email, password);

            //  UsernamePasswordAuthenticationToken -> Authentication 상속받음.
            // SecurityContetHolder.getContext()에 등록될 Authentication 객체
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
            //아직 인증되지 않은 Authentication객체를 생성한 것이고 추후 모든 인증이 완료되면 인증된 생성자로 Authentication객체가 생성된다는 것을 알아두자!

            // AuthenticationManager 는
            // AuthenticationProvider라는 클래스 객체를 관리한다
            // AuthenticationProvider는 실제 인증 로직이 담긴 객체
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        // 로그인 성공시 호출되는 메서드.
        log.info("successfulAuthentication 호출됨");

        PrincipalDetails user = (PrincipalDetails) authentication.getPrincipal();

        log.info("principalDetails getUsername: {}", user.getUsername());

        System.out.println(user.getRegisterStatus());
        String access_token = JwtTokenService.createAccessToken(user.getUsername(), user.getName(), user.getRole(), user.getRegisterStatus());
        String refresh_token = JwtTokenService.createRefreshToken();

        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token",access_token);
        tokens.put("refresh_token",refresh_token);

        response.setContentType(APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.error("unsuccessful Authentication");

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("code", HttpStatus.UNAUTHORIZED.value());
        body.put("error", failed.getMessage());

        new ObjectMapper().writeValue(response.getOutputStream(), body);
    }
}
