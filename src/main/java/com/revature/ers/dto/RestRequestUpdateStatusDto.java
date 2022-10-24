package com.revature.ers.dto;

import com.revature.ers.model.Status;
import com.revature.ers.model.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class RestRequestUpdateStatusDto {

    private int resolverId;
    private Status status;
    private String timeResolved;

}
