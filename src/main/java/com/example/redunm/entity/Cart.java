package com.example.redunm.entity;

import com.example.redunm.modellist.DataModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "cart")
public class Cart {

    @Id
    private String id;
    private String userId;
    private List<DataModel> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public Cart(String userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<DataModel> getItems() {
        return items;
    }

    public void setItems(List<DataModel> items) {
        this.items = items;
    }

    // equals 및 hashCode 메서드
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;

        return Objects.equals(userId, cart.userId);
    }

    @Override
    public int hashCode() {
        return userId != null ? userId.hashCode() : 0;
    }

    // toString 메서드
    @Override
    public String toString() {
        return "Cart{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", items=" + items +
                '}';
    }
}
