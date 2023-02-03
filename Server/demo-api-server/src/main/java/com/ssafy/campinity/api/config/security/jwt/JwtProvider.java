package com.ssafy.campinity.api.config.security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.campinity.api.dto.res.TokenResponse;
import com.ssafy.campinity.core.exception.ForbiddenException;
import com.ssafy.campinity.core.entity.member.Member;
import com.ssafy.campinity.core.repository.redis.RedisDao;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final ObjectMapper objectMapper;
    private final RedisDao redisDao;

    @Value("${spring.jwt.key}")
    private String key;

    @Value("${spring.jwt.live.atk}")
    private Long atkLive;

    @Value("${spring.jwt.live.rtk}")
    private Long rtkLive;

    @PostConstruct
    protected void init() {
        key = Base64.getEncoder().encodeToString(key.getBytes());
    }

    public TokenResponse createTokensByLogin(Member member, Boolean isExist) throws JsonProcessingException {
        Subject atkSubject = Subject.atk(
                UUID.fromString(member.getUuid().toString()),
                member.getEmail(),
                member.getName());

        Subject rtkSubject = Subject.rtk(
                UUID.fromString(member.getUuid().toString()),
                member.getEmail(),
                member.getName());

        String atk = createToken(atkSubject, atkLive);
        String rtk = createToken(rtkSubject, rtkLive);
        redisDao.setValues(member.getEmail(), rtk, Duration.ofMillis(rtkLive));
        return TokenResponse.builder()
                .accessToken(atk)
                .refreshToken(rtk)
                .tokenType("Bearer")
                .isExist(isExist).build();
    }

    private String createToken(Subject subject, Long tokenLive) throws JsonProcessingException {
        String subjectStr = objectMapper.writeValueAsString(subject);
        Claims claims = Jwts.claims()
                .setSubject(subjectStr);
        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + tokenLive))
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .compact();
    }

    public Subject getSubject(String atk) throws JsonProcessingException {
        String subjectStr = Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(atk).getBody().getSubject();
        return objectMapper.readValue(subjectStr, Subject.class);
    }

    public TokenResponse reissueAtk(Member member) throws JsonProcessingException {
        String rtkInRedis = redisDao.getValues(member.getEmail());
        if (Objects.isNull(rtkInRedis)) throw new ForbiddenException("인증 정보가 만료되었습니다.");
        Subject atkSubject = Subject.atk(
                member.getUuid(),
                member.getEmail(),
                member.getName());
        String atk = createToken(atkSubject, atkLive);
        return TokenResponse.builder()
                .accessToken(atk)
                .refreshToken(null)
                .tokenType("Bearer")
                .isExist(true).build();
    }
}