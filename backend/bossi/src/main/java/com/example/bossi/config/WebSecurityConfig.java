package com.example.bossi.config;

import com.example.bossi.filter.CustomAuthenticationFilter;
import com.example.bossi.filter.CustomAuthorizationFilter;
import com.example.bossi.oauth2.CustomOAuth2UserService;
import com.example.bossi.oauth2.OAuth2SuccessHandler;
import com.example.bossi.repository.UserRepository;
import com.example.bossi.service.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 됨
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtTokenService jwtService;
    private final UserRepository userRepository;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    public CustomAuthenticationFilter getCustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new CustomAuthenticationFilter(authenticationManager);
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationConfiguration.getAuthenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/users/login");

        http
                .httpBasic().disable()
                .csrf().disable()
                .cors();

        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .addFilter(getCustomAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
                .addFilterBefore(new CustomAuthorizationFilter(jwtService, userRepository), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/users/login").permitAll()
                .requestMatchers("/api/v1/users/join").permitAll()
                .requestMatchers("/api/v1/oauth2/**").permitAll()
                .requestMatchers("/seller/**").hasRole("ROLE_SELLER")
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

        return http.build();
    }


}