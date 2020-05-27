package com.example.hookahshop_diploma.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_name")
    private String name;
    @Column(name = "product_price")
    private Double price;
    @Column(name = "product_description", length = 10000)
    private String description;
    @Column(name = "product_amount")
    private Long amount;
    @Column(name = "product_image", length = 500)
    private String imageUrl;
    @Column(name = "product_type")
    private String type;
    @ManyToMany
    private List<CharacterValue> characterValue;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ShopProductFeedback> shopProductFeedbacks;
}
