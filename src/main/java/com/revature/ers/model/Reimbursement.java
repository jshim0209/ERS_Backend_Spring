package com.revature.ers.model;

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

    @ManyToOne(cascade=CascadeType.ALL)
    private User author;

    @OneToOne(cascade=CascadeType.ALL)
    private User resolver;

    @OneToOne(cascade=CascadeType.ALL)
    private Status status;

    @OneToOne(cascade=CascadeType.ALL)
    private Type type;

}

