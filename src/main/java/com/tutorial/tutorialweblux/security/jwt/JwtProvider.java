package com.tutorial.tutorialweblux.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .signWith(getKey(secret))
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey(secret)).build().parseClaimsJws(token).getBody();
    }

    public String getSubject(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey(secret)).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validate(String token){
        try {
            Jwts.parserBuilder().setSigningKey(getKey(secret)).build().parseClaimsJws(token).getBody();
            return true;
        } catch (ExpiredJwtException e) {
            log.error("token expired");
        } catch (UnsupportedJwtException e) {
            log.error("token unsupported");
        } catch (MalformedJwtException e) {
            log.error("token malformed");
        } catch (SignatureException e) {
            log.error("bad signature");
        } catch (IllegalArgumentException e) {
            log.error("illegal args");
        }
        return false;
    }

    private Key getKey(String secret) {
        byte[] secretBytes = Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }
}
