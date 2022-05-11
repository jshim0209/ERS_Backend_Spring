package com.revature.ERS.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class LoginDto {

    private String username;
    private String password;
}