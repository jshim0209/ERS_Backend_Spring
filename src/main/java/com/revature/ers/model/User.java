package com.revature.ers.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table (name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    @OneToOne(cascade=CascadeType.ALL)
    private UserRole role;

}
