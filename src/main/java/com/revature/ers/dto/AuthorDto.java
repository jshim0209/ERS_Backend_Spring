package com.revature.ers.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AuthorDto {

    private int userId;
    private String username;
}
