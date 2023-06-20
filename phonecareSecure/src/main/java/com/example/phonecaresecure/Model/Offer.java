package com.example.phonecaresecure.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @PositiveOrZero(message = "price should not be less than zero")
    @NotNull(message = "price should not be  empty")
    private double price;
    @NotEmpty(message = "offerStatus should not be empty")

    private String offerStatus;
    //سو تشيك انه وقت في الحاضر بعد التيست
    private LocalDate createdAt;
    //سو تشيك انه وقت في الحاضر بعد التيست
    @FutureOrPresent(message = "Estimated deadline should not be in the past")
    private LocalDate estemateddeadline;

    private String reason;

    @ManyToOne
    @JoinColumn(name = "provider_id",referencedColumnName = "user_id")
    @JsonIgnore
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "order_id",referencedColumnName = "id")
    @JsonIgnore
    private Order order;


}
