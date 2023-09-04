package com.example.bossi.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.bossi.repository.user.UserRepository;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;

import static com.example.bossi.config.jwt.JwtProperties.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtTokenService {

    private final UserRepository userRepository;

    public static String getEmail(String token){
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJwt(token).getBody().get("email", String.class);
    }
    public static boolean isExpired(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token).getBody().getExpiration().before(new Date()); // 토큰 만료가 지금보다 전이면
    }

    /**
     * AccessToken 생성 메소드
     */
    public static String createAccessToken(String email, String nickName, String role, Boolean registerStatus){
        Date now = new Date();

        return JWT.create()
                .withClaim("email", email)
                .withClaim("nickName", nickName)
                .withClaim("registerStatus", registerStatus)
                .withClaim("role", role)
                .withSubject(ACCESS_HEADER)
                .withSubject(ACCESS_HEADER)
                .withExpiresAt(new Date(now.getTime() + EXPIRATION_TIME_ACCESS))
                .sign(Algorithm.HMAC512(SECRET))
                ;
    }

/*    public String createAccessToken(String name, String role, Boolean registerStatus) {
        Date now = new Date();

        return JWT.create()
                .withClaim("name", name)
                .withClaim("role", role)
                .withClaim("registerStatus", registerStatus)
                .withSubject(ACCESS_HEADER)
                .withExpiresAt(new Date(now.getTime() + EXPIRATION_TIME_ACCESS))
                .sign(Algorithm.HMAC512(SECRET));
    }*/

    public static String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_HEADER)
                .withExpiresAt(new Date(now.getTime() + EXPIRATION_TIME_REFRESH))
                .sign(Algorithm.HMAC512(SECRET));
    }

    public Optional<String> extractEmail(String accessToken) {
        try {
            // 토큰 유효성 검사하는 데에 사용할 알고리즘이 있는 JWT verifier builder 반환
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(SECRET))
                    .build() // 반환된 빌더로 JWT verifier 생성
                    .verify(accessToken) // accessToken을 검증하고 유효하지 않다면 예외 발생
                    .getClaim("email") // claim(Emial) 가져오기
                    .asString());
        } catch (Exception e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    public Optional<String> extractAccessToken(HttpServletRequest request){
        return Optional.ofNullable(request.getHeader(ACCESS_HEADER))
                .filter(refreshToken -> refreshToken.startsWith(TOKEN_PREFIX))
                .map(refreshToken -> refreshToken.replace(TOKEN_PREFIX, ""));
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(REFRESH_HEADER))
                .filter(refreshToken -> refreshToken.startsWith(TOKEN_PREFIX))
                .map(refreshToken -> refreshToken.replace(TOKEN_PREFIX, ""));
    }

    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader(ACCESS_HEADER, accessToken);
        log.info("재발급된 Access Token : {}", accessToken);
    }

    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken){
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader(ACCESS_HEADER, accessToken);
        response.setHeader(REFRESH_HEADER, refreshToken);
        log.info("Access Token, Refresh Token 헤더 설정 완료");
    }

    public void updateRefreshToken(String email, String refreshToken) {
        userRepository.findByEmail(email)
                .ifPresentOrElse(
                        user -> user.updateRefreshToken(refreshToken),
                        () -> new Exception("일치하는 회원이 없습니다.")
                );
    }

    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(SECRET)).build().verify(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            return false;
        }
    }
}
