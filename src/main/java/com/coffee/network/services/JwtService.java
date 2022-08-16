package com.coffee.network.services;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  private long ttl;
  private Key key;

  public JwtService(
    @Value("${jwt.ttl}") long ttl,
    @Value("${jwt.key}") String signKey
  ) {
    this.ttl = ttl;
    this.key = Keys.hmacShaKeyFor(signKey.getBytes());
  }

  public String generateToken(String username) {
    return Jwts.builder()
      .setSubject(username)
      .setIssuedAt(new Date())
      .setExpiration(new Date(System.currentTimeMillis() + ttl * 1000))
      .signWith(key)
      .compact();
  }

}
