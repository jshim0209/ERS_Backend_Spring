package com.revature.ERS.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "reimbursements")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Reimbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private double amount;

    @Column
    private String timeSubmitted;

    @Column
    private String timeResolved;

    @Column
    private String description;

    @Column
    private String receipt;

    @ManyToOne
    private User author;

    @OneToOne
    private User resolver;

    @OneToOne
    private Status status;

    @OneToOne
    private Type type;
}

