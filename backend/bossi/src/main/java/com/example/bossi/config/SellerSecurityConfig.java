package com.example.bossi.config;

import com.example.bossi.config.auth.CustomAuthenticationProvider;
import com.example.bossi.config.auth.CustomSellerAuthenticationProvider;
import com.example.bossi.config.auth.PrincipalDetailsService;
import com.example.bossi.config.auth.SellerDetailsService;
import com.example.bossi.config.handler.CustomAccessDeniedHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import com.example.bossi.filter.CustomAuthenticationFilter;
import com.example.bossi.filter.CustomAuthorizationFilter;
import com.example.bossi.filter.CustomSellerAuthenticationFilter;
import com.example.bossi.filter.CustomSellerAuthorizationFilter;
import com.example.bossi.oauth2.CustomOAuth2UserService;
import com.example.bossi.oauth2.OAuth2SuccessHandler;
import com.example.bossi.repository.seller.SellerRepository;
import com.example.bossi.repository.user.UserRepository;
import com.example.bossi.service.jwt.JwtSellerTokenService;
import com.example.bossi.service.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨
@RequiredArgsConstructor
@Order(1)
@Slf4j
public class SellerSecurityConfig {

    private final JwtSellerTokenService jwtSellerTokenService;
    private final SellerRepository sellerRepository;
    private final SellerDetailsService sellerDetailsService;
    private final PasswordEncoder passwordEncoder;

    private static final String[] DOC_URLS = {
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    @Bean
    public CustomSellerAuthenticationProvider customSellerAuthenticationProvider() {
        return new CustomSellerAuthenticationProvider(sellerDetailsService, passwordEncoder);
    }

    @Bean
    public AuthenticationManager sellerAuthenticationManager(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.authenticationProvider(new CustomSellerAuthenticationProvider(sellerDetailsService, passwordEncoder));
        return authenticationManagerBuilder.build();
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors();

        http
                .securityMatcher("/api/v1/seller/login")
                /*.authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/v1/seller/**").hasAnyAuthority("BOSS") // "/api/v1/seller/login" 경로에 대한 설정
                )*/
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new CustomSellerAuthenticationFilter(sellerAuthenticationManager(null)))
                .addFilterBefore(new CustomSellerAuthorizationFilter(jwtSellerTokenService, sellerRepository), UsernamePasswordAuthenticationFilter.class);

        http.
                authorizeHttpRequests()
                .requestMatchers("/api/v1/seller/product/**").permitAll();
        return http.build();
    }


}