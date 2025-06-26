package com.petd.profileservice.customSercurity;

import com.petd.profileservice.exception.AppException;
import com.petd.profileservice.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {

  @Value("${jwt.secret}")
  private String secret;

  @Getter
  @Value("${jwt.expiration}")
  private long expiration;

  private Key jwtKey;

  @PostConstruct
  public void init() {
    byte[] keyBytes = Base64.getDecoder().decode(secret);
    this.jwtKey = Keys.hmacShaKeyFor(keyBytes);
  }
  public String generateToken(String username) throws Exception {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
        .signWith(jwtKey)
        .compact();
  }

  public String getUsernameFromToken(String token) {
    validateToken(token);
    return Jwts.parserBuilder().setSigningKey(jwtKey).build()
        .parseClaimsJws(token).getBody().getSubject();
  }

  public void validateToken(String token) {
    try {
      Claims claims = Jwts.parserBuilder()
          .setSigningKey(jwtKey)
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException ex) {
      throw new AppException(ErrorCode.TOKEN_EXPIRED);
    } catch (UnsupportedJwtException | MalformedJwtException | SignatureException |
             IllegalArgumentException ex) {
      throw new AppException(ErrorCode.TOKEN_INVALID);
    }
  }

}
