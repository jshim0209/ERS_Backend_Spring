package com.revature.ERS.dto;

import com.revature.ERS.model.UserRole;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class SignUpDto {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private UserRole userRole;
}
