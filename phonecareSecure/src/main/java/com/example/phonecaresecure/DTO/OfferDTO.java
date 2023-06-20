package com.example.phonecaresecure.DTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
@Data
@AllArgsConstructor
public class OfferDTO {

    @PositiveOrZero(message = "price should not be less than zero")
    @NotNull(message = "price should not be  empty")
    private Integer price;
    @FutureOrPresent(message = "Estimated deadline should not be in the past")
    private LocalDate estemateddeadline;
}
