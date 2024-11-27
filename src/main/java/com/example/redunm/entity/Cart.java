package com.example.redunm.entity;

import com.example.redunm.model.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "cart" , cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

}
