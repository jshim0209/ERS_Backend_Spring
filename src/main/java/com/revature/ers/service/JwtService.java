package com.revature.ers.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ers.dto.UserDto;
import com.revature.ers.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
    private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String createJwt(User user) throws JsonProcessingException {
        UserDto dto = new UserDto(user.getId(), user.getFirstName(),
                user.getLastName(), user.getEmail(), user.getUsername(), user.getRole().getRole());

        return Jwts.builder()
                .claim("user_dto", new ObjectMapper().writeValueAsString(dto))
                .signWith(key)
                .compact();
    }
    public static boolean parseJwt(String jwt) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);

            return (checkExpiration(claims.getBody()));
        } catch (IllegalArgumentException | SignatureException | ExpiredJwtException e) {
            return false;
        }
    }

    private static boolean checkExpiration(Claims claims) {
        return claims.getExpiration().after(Date.from(Instant.now()));
    }

    public static String setExpiration(String jwt, Date newDate){
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt);
            Claims newClaims = claims.getBody().setExpiration(newDate);
            return Jwts.builder()
                    .setClaims(newClaims)
                    .signWith(key)
                    .compact();

        } catch (IllegalArgumentException | SignatureException e) {
            return jwt;
        }
    }

}