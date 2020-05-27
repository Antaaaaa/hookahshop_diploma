package com.example.hookahshop_diploma.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
// Відгуки про певний один товар, наприклад
public class ShopProductFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000)
    private String feedback;
    private Integer rating;
    @ManyToOne
    private ShopUser user;
    @ManyToOne
    private ShopProduct shopProduct;
}
