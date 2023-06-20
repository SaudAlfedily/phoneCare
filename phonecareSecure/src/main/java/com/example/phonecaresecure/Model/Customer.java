package com.example.phonecaresecure.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Customer {
    @Id
    private Integer id;
    @Email(message = "Customer mail not valid")
    @NotEmpty(message = "Customer mail should not be empty")
    private String mail;

    @NotEmpty(message = "Customer name should not be empty")
    private String name;

    @NotEmpty(message = "Customer mail should not be empty")
    private String phone;

    @PositiveOrZero(message = "loyalty points should not be less than zero")
    @NotNull(message = "loyalty points  should not be empty")
    private double loyaltypoints;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
    private Set<Order> orders;


}
