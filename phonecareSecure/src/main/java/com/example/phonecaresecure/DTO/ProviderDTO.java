package com.example.phonecaresecure.DTO;


import com.example.phonecaresecure.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProviderDTO {

    @JsonIgnore
    @Transient
    private final String rol1="PROVIDER";


    @NotEmpty(message = "user name should not be empty")
    private String username;
    @NotEmpty(message = "password should not be empty")
    @Pattern(regexp = "^(?=.?[A-Z])(?=.?[a-z])(?=.*?[0-9]).{8,}$", message = "Password should be Minimum eight characters, at least one uppercase letter, one lowercase letter and one number")
    private String password;
    @NotEmpty(message = "role should not be empty")
    @Pattern(regexp = "^("+rol1+")",message = "Role can only be (CUSTOMER)")
    private String role;

    @Email(message = "Provider mail not valid")
    @NotEmpty(message = "Provider mail should not be empty")
    private String mail;
    @NotEmpty(message = "Provider name should not be empty")
    private String name;
    @NotEmpty(message = "Provider phone should not be empty")
    private String phone;
    @NotEmpty(message = "Provider info should not be empty")
    private String info;

    private User user;
}
