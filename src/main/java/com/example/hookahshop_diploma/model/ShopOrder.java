package com.example.hookahshop_diploma.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class ShopOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phone;
    private String email;
    private String name;
    private String surname;
    @Column(length = 1000)
    private String comment;
    private Boolean isPaid;
    private Double summary;
    private String date;
    @OneToOne
    private Cart cart;
    @ManyToOne
    private ShopUser user;
    @ManyToOne
    private ShopOrderStatus status;
    @ManyToOne
    private Address address;
}
