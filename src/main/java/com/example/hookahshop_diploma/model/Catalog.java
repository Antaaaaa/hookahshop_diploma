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
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "catalog_name")
    private String name;
    @Column(name = "catalog_icon")
    private String image;
    @OneToMany(cascade = CascadeType.ALL)
    private List<SubCatalog> subCatalogList;
}
