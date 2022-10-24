package com.revature.ers.dto;

import com.revature.ers.model.Status;
import com.revature.ers.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RestResponseUpdateStatusDto {

    private int id;
    private User updatedResolver;
    private Status udpatedStatus;
    private String timeResolved;
}
