package com.example.hookahshop_diploma.model;

import com.example.hookahshop_diploma.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class ShopUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ShopProductFeedback> shopProductFeedbackList;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Feedback> feedbacksList;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ShopOrder> shopOrders;
    @ManyToMany
    private List<ShopProduct> likedProduct;

    public ShopUser(String username, String email, String password, Role role){
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
