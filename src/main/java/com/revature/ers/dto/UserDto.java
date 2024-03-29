package com.revature.ers.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDto {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String userRole;

}
