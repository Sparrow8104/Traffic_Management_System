package com.smarttraffic.traffic_management_system.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtils {

        private final String SECRET="ThisIsASecretKeyForJWTsMustBeLongEnough123!";//here a 32 bytes of string will be required to function well
        private final SecretKey key= Keys.hmacShaKeyFor(SECRET.getBytes());
        public String generateToken(String username){
            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        }

        public String extractUsername(String token){
            Claims body=extractBody(token);
            return body.getSubject();
        }
        public boolean validateToken(String token,String username){
           return username.equals(extractUsername(token))&&!isExpired(token);
        }

    private Claims extractBody(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isExpired(String token){
            return extractBody(token).getExpiration().before(new Date());
        }
}
