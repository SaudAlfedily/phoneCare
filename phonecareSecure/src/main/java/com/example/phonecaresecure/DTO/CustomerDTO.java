package com.example.phonecaresecure.DTO;


import com.example.phonecaresecure.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTO {

    @JsonIgnore
    @Transient
    private final String rol1="CUSTOMER";


    @NotEmpty(message = "user name should not be empty")
    private String username;
    @NotEmpty(message = "password should not be empty")
    @Pattern(regexp = "^(?=.?[A-Z])(?=.?[a-z])(?=.*?[0-9]).{8,}$", message = "Password should be Minimum eight characters, at least one uppercase letter, one lowercase letter and one number")
    private String password;
    @NotEmpty(message = "role should not be empty")
    @Pattern(regexp = "^("+rol1+")",message = "Role can only be (CUSTOMER)")
    private String role;

    @Email(message = "Customer mail not valid")
    @NotEmpty(message = "Customer mail should not be empty")
    private String mail;

    @NotEmpty(message = "Customer name should not be empty")
    private String name;

    @NotEmpty(message = "Customer mail should not be empty")
    private String phone;

//    @PositiveOrZero(message = "balance should not be less than zero")
//    @NotNull(message = "balance should not be empty")
//    private double balance;

    private User user;

}
