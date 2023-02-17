package com.ssafy.campinity.api.config.security.jwt;

import com.ssafy.campinity.api.service.MemberDetailService;
import com.ssafy.campinity.core.repository.redis.RedisDao;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final MemberDetailService memberDetailService;
    private final RedisDao redisDao;

    public JwtAuthenticationFilter(JwtProvider jwtProvider, MemberDetailService memberDetailService, RedisDao redisDao) {
        this.jwtProvider = jwtProvider;
        this.memberDetailService = memberDetailService;
        this.redisDao = redisDao;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (!Objects.isNull(authorization)) {
            String atk = authorization.substring(7);
            try {
                Subject subject = jwtProvider.getSubject(atk);
                String requestURI = request.getRequestURI();
                if (subject.getType().equals("RTK") && !requestURI.equals("/api/v4/members/reissue")) {
                    throw new JwtException("토큰을 확인하세요.");
                }

                String isLogout = redisDao.getValues(atk);
                if (ObjectUtils.isEmpty(isLogout)) {
                    UserDetails userDetails = memberDetailService.loadUserByUsername(subject.getEmail());
                    Authentication token = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            } catch (JwtException e) {
                request.setAttribute("exception", e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}