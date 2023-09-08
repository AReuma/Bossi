package com.example.bossi.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.bossi.config.auth.PrincipalDetails;
import com.example.bossi.config.jwt.JwtProperties;
import com.example.bossi.entity.User;
import com.example.bossi.repository.user.UserRepository;
import com.example.bossi.service.jwt.JwtTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.example.bossi.config.jwt.JwtProperties.SECRET;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter { // 요청 당 한번만 실행되록 보장되는 필터
    // 권한 요청을 하고 있을때 필터 발생

    private final UserRepository userRepository;

    private final JwtTokenService jwtService;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    public CustomAuthorizationFilter(JwtTokenService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("필터 순서 : CustomAuthorizationFilter");
        log.info(request.getServletPath());

        // 패턴이 제대로 안됨.
        if(request.getServletPath().equals("/api/v1/users/login") || request.getServletPath().equals("/api/v1/oauth2/redirect") || request.getServletPath().equals("/swagger-ui")) {
            log.info("nonono!");
            filterChain.doFilter(request, response);
        }else { // 로그인 외 모든 요청에 filter 처리

            String authorizationHeader = request.getHeader(AUTHORIZATION);

            if(authorizationHeader != null && authorizationHeader.startsWith(JwtProperties.TOKEN_PREFIX)){
                try {
                    String token = authorizationHeader.replace(JwtProperties.TOKEN_PREFIX, "");
                    log.info("token" + token);


                    JwtTokenService jwtTokenService = new JwtTokenService(userRepository);
                    Optional<String> s = jwtTokenService.extractAccessToken(request);
                    System.out.println("====");
                    System.out.println(s);
                    System.out.println("====");

                    Algorithm algorithm = Algorithm.HMAC512(JwtProperties.SECRET.getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);

                    String email = JWT.require(Algorithm.HMAC512(SECRET)).build().verify(token).getClaim("email").asString();
                    String id = decodedJWT.getSubject();
                    log.info("id: "+email);

                    User memberEntity = userRepository.findUserByEmail(email);
                    PrincipalDetails principalDetails = new PrincipalDetails(memberEntity);


                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, null, principalDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    filterChain.doFilter(request, response);
                }catch (Exception e){
                    log.error("Error logging in : {}", e.getMessage());
                    response.setHeader("error", e.getMessage());
                    response.setStatus(FORBIDDEN.value());
                    //response.sendError(FORBIDDEN.value());

                    Map<String, String> error = new HashMap<>();
                    error.put("error_message",e.getMessage());

                    response.setContentType(APPLICATION_JSON_VALUE);

                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }

           /* log.info("authorizationHeader : {}", request.getServletPath());
            log.info("authorizationHeader : {}", jwtService.extractAccessToken(request));

            String refreshToken = jwtService.extractRefreshToken(request)
                    .filter(jwtService::isTokenValid)
                    .orElse(null);

            // 리프레시 토큰이 요청 헤더에 존재했다면, 사용자가 AccessToken이 만료되어서
            // RefreshToken까지 보낸 것이므로 리프레시 토큰이 DB의 리프레시 토큰과 일치하는지 판단 후,
            // 일치한다면 AccessToken을 재발급해준다.
            if (refreshToken != null) {
                checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
                return; // RefreshToken을 보낸 경우에는 AccessToken을 재발급 하고 인증 처리는 하지 않게 하기위해 바로 return으로 필터 진행 막기
            }

            // RefreshToken이 없거나 유효하지 않다면, AccessToken을 검사하고 인증을 처리하는 로직 수행
            // AccessToken이 없거나 유효하지 않다면, 인증 객체가 담기지 않은 상태로 다음 필터로 넘어가기 때문에 403 에러 발생
            // AccessToken이 유효하다면, 인증 객체가 담긴 상태로 다음 필터로 넘어가기 때문에 인증 성공
            if (refreshToken == null) {
                checkAccessTokenAndAuthentication(request, response, filterChain);
            }*/
        }else {
            filterChain.doFilter(request, response);
        }
    }

    /*public void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {

        userRepository.findByRefreshToken(refreshToken)
                .ifPresent(user -> {
                    String reIssuedRefreshToken = reIssueRefreshToken(user);
                    jwtService.sendAccessAndRefreshToken(response, jwtService.createAccessToken(user.getEmail(), user.getNickName(), user.getRole().getKey(), user.getRegisterStatus()),
                            reIssuedRefreshToken);
                });
    }

    private String reIssueRefreshToken(User user) {
        String reIssuedRefreshToken = jwtService.createRefreshToken();
        user.updateRefreshToken(reIssuedRefreshToken);
        userRepository.saveAndFlush(user);
        return reIssuedRefreshToken;
    }

    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        log.info("checkAccessTokenAndAuthentication() 호출");
        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .ifPresent(accessToken -> jwtService.extractEmail(accessToken)
                        .ifPresent(email -> userRepository.findByEmail(email)
                                .ifPresent(this::saveAuthentication)));

        filterChain.doFilter(request, response);
    }

    public void saveAuthentication(User myUser) {
        String password = myUser.getPassword();
        if (password == null) { // 소셜 로그인 유저의 비밀번호 임의로 설정 하여 소셜 로그인 유저도 인증 되도록 설정
            password = UUID.randomUUID()+ myUser.getEmail();
        }

        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getEmail())
                .password(password)
                .roles(myUser.getRole().name())
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }*/
        }
}
