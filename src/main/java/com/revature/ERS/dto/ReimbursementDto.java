package com.revature.ERS.dto;

import com.revature.ERS.model.Status;
import com.revature.ERS.model.Type;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ReimbursementDto {

    private int id;
    private double amount;
    private String timeSubmitted;
    private String timeResolved;
    private String description;
    private String receipt;
    private AuthorDto author;
    private ResolverDto resolver;
    private Status status;
    private Type type;
}
