package com.ssafy.campinity.api.config.security;

import com.ssafy.campinity.api.config.security.jwt.JwtAuthenticationFilter;
import com.ssafy.campinity.api.config.security.jwt.JwtProvider;
import com.ssafy.campinity.api.service.MemberDetailService;
import com.ssafy.campinity.core.repository.redis.RedisDao;
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
    private final RedisDao redisDao;

    private static final String[] AUTH_ARR = {
            "/v2/api-docs",
            "/swagger/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/api/v4/members/login-kakao",
            "/images/**/**",
            "/chat/**/**"
    };

    public AppConfig(JwtProvider jwtProvider, MemberDetailService memberDetailService, CustomAuthenticationEntryPoint customAuthenticationEntryPoint, RedisDao redisDao) {
        this.jwtProvider = jwtProvider;
        this.memberDetailService = memberDetailService;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.redisDao = redisDao;
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
                .antMatchers(AUTH_ARR).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider,  memberDetailService, redisDao),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}