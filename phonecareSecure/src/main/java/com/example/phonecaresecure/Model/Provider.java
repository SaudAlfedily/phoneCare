package com.example.phonecaresecure.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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
public class Provider {
    @Id
    private Integer id;
    @Email(message = "Provider mail not valid")
    @NotEmpty(message = "Provider mail should not be empty")
    private String mail;
    @NotEmpty(message = "Provider name should not be empty")
    private String name;
    @NotEmpty(message = "Provider phone should not be empty")
    private String phone;
    @NotEmpty(message = "Provider info should not be empty")
    private String info;
    private Double totalRate;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "provider")
    private Set<Offer> offers;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "provider")
    private Set<Review> reviews;

}
