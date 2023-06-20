package com.example.phonecaresecure.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "phone information should not be empty")
    private String phoneinfo;

    @NotEmpty(message = "orderStatus should not be empty")
    private String orderStatus;
// شيك الديت مو في الماضي
    private LocalDate createdAt;
    @NotEmpty(message = "problem description should not be empty")
    private String problemdescription;

    @ManyToOne
    @JoinColumn(name = "customer_id",referencedColumnName = "user_id")
    @JsonIgnore
    private Customer customer;


    @OneToOne(cascade = CascadeType.ALL,mappedBy = "order")
    @PrimaryKeyJoinColumn
    private Review review;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "order")
    private Set<Offer> offers;


}
