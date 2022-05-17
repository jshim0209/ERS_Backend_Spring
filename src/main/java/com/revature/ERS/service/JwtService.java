package com.revature.ERS.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ERS.dto.UserDto;
import com.revature.ERS.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;


import java.security.Key;

@Service
public class JwtService {

    private Key key;

    public JwtService() {
        byte[] secret = "dsalkgjasgieo&$(@&$*(Gkljdgalskegjoiejladsgkjahslkhjed".getBytes();
        key = Keys.hmacShaKeyFor(secret);
    }

    public String createJwt(User user) throws JsonProcessingException {
        UserDto dto = new UserDto(user.getId(), user.getFirstName(),
                user.getLastName(), user.getEmail(), user.getUsername(), user.getRole().getRole());

        return Jwts.builder()
                .claim("user_dto", new ObjectMapper().writeValueAsString(dto))
                .signWith(key)
                .compact();
    }

    public UserDto parseJwt(String jwt) throws JsonProcessingException {
        Jws<Claims> token = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);

        String dtoString = (String)token.getBody().get("user_dto");

        return (new ObjectMapper()).readValue(dtoString, UserDto.class);
    }
}
