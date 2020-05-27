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
public class SubCatalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "subcatalog_name")
    private String subname;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ShopProduct> shopProduct;
}
