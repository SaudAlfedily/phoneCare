package com.example.phonecaresecure.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewDTO {

    //when security complete no need for order id


    @NotEmpty(message = "review should not be empty")
    private String review;

    @NotNull(message = "Stars should not be empty")
    @Min(value = 0,message = "min star 0")
    @Max(value = 5,message = "max star 5")
    private Integer Stars;
}
