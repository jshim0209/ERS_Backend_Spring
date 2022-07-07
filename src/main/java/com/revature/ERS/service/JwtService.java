package com.revature.ERS.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ERS.dto.UserDto;
import com.revature.ERS.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.sql.Date;
import java.time.Instant;

@Service
public class JwtService {
    private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static JwtService instance;

    public String createJwt(User user) throws JsonProcessingException {
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
}
