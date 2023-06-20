package com.example.phonecaresecure.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class OrderDTO {
    //when security complete no need for customer id
    //private Integer customer_id;

    @NotEmpty(message = "phoneinfo should not be empty")
    private String phoneinfo;


    @NotEmpty(message = "problem description should not be empty")
    private String problemdescription;

}
