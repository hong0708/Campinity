package com.ssafy.campinity.api.config.security;

import com.ssafy.campinity.api.config.security.jwt.JwtAuthenticationFilter;
import com.ssafy.campinity.api.config.security.jwt.JwtProvider;
import com.ssafy.campinity.api.service.MemberDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AppConfig {

    private final JwtProvider jwtProvider;
    private final MemberDetailService memberDetailService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public AppConfig(JwtProvider jwtProvider, MemberDetailService memberDetailService, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.jwtProvider = jwtProvider;
        this.memberDetailService = memberDetailService;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v4/members/login-kakao").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider,  memberDetailService),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}