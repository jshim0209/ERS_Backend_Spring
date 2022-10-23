package com.revature.ers.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class TokenResponse {

    private String jwt;
    private int userId;
    private String username;
    private String userRole;
    private String firstName;

}
