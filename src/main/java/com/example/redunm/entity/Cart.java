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
    private List<CartItem> items;

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

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public void addItem(DataModel dataModel) {
        for (CartItem item : items) {
            if (item.getDataModel().getId().equals(dataModel.getId())) {
                item.incrementQuantity();
                return;
            }
        }
        items.add(new CartItem(dataModel));
    }

    public void removeItem(String modelId) {
        items.removeIf(item -> item.getDataModel().getId().equals(modelId));
    }

    public double calculateTotalPrice() {
        return items.stream()
                .mapToDouble(item -> item.getDataModel().getPrice() * item.getQuantity())
                .sum();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(userId, cart.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
