package com.example.bossi.config;

import com.example.bossi.config.auth.CustomAuthenticationProvider;
import com.example.bossi.config.auth.CustomSellerAuthenticationProvider;
import com.example.bossi.config.auth.PrincipalDetailsService;
import com.example.bossi.config.auth.SellerDetailsService;
import com.example.bossi.config.handler.CustomAccessDeniedHandler;
import com.example.bossi.entity.Role;
import com.example.bossi.filter.CustomAuthenticationFilter;
import com.example.bossi.filter.CustomAuthorizationFilter;
import com.example.bossi.oauth2.CustomOAuth2UserService;
import com.example.bossi.oauth2.OAuth2SuccessHandler;
import com.example.bossi.repository.user.UserRepository;
import com.example.bossi.service.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Slf4j
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨
@RequiredArgsConstructor
@Order(2)
public class WebSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtTokenService jwtService;
    private final UserRepository userRepository;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final PrincipalDetailsService principalDetailsService;
    private final SellerDetailsService sellerDetailsService;

    private static final String[] DOC_URLS = {
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.authenticationProvider(new CustomAuthenticationProvider(principalDetailsService, passwordEncoder()));
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {

        http
                .httpBasic().disable()
                .csrf().disable()
                .cors();

        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .addFilter(new CustomAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
                .addFilterBefore(new CustomAuthorizationFilter(jwtService, userRepository), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/manager/**").hasAnyAuthority("ADMIN")
                .requestMatchers(DOC_URLS).permitAll()
                .anyRequest().permitAll();

        http
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .redirectionEndpoint()
                .baseUri("/login/oauth2/code/*")
                .and()
                .successHandler(oAuth2SuccessHandler);

        http
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler());

        return http.build();
    }


}