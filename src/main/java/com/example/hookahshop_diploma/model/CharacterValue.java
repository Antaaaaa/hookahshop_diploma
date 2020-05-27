package com.example.hookahshop_diploma.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class CharacterValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private Character character;
    @OneToOne(cascade = CascadeType.ALL)
    private Value value;
//    @ManyToMany
//    private List<ShopProduct> characterValue;
}
