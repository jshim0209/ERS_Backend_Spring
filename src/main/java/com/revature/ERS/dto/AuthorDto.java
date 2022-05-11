package com.revature.ERS.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AuthorDto {

    private int id;
    private String username;
    private String email;
}
