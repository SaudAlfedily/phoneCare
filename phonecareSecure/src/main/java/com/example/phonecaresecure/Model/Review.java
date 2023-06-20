package com.example.phonecaresecure.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "review should not be empty")
    private String review;
    @NotNull(message = "Stars should not be empty")
    @Min(value = 0,message = "min star 0")
    @Max(value = 5,message = "max star 5")
    private Integer Stars;


    @OneToOne
    @MapsId
    @JsonIgnore
    private Order order;


    @ManyToOne
    @JoinColumn(name = "provider_id",referencedColumnName = "user_id")
    @JsonIgnore
    private Provider provider;
}
