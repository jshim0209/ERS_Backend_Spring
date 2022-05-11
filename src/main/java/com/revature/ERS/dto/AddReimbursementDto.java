package com.revature.ERS.dto;

import com.revature.ERS.model.Type;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AddReimbursementDto {

    private double amount;
    private String timeSubmitted;
    private String description;
    private String receipt;
    private Type type;

}
