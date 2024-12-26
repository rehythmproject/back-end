package com.example.redunm.cart;

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
    private String username; // 기존 userId -> username으로 변경
    private List<CartItem> items;

    // 기본 생성자
    public Cart() {
        this.items = new ArrayList<>();
    }

    // username을 포함하는 생성자
    public Cart(String username) {
        this.username = username;
        this.items = new ArrayList<>();
    }

    // Getter 및 Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username; // userId -> username 변경
    }

    public void setUsername(String username) { // userId -> username 변경
        this.username = username;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    // 장바구니에 아이템 추가
    public void addItem(DataModel dataModel) {
        for (CartItem item : items) {
            if (item.getDataModel().getName().equals(dataModel.getName())) {
                item.incrementQuantity();
                return;
            }
        }
        items.add(new CartItem(dataModel));
    }

    // 장바구니에서 아이템 제거 (name 기준)
    public void removeItemByName(String name) {
        items.removeIf(item -> item.getDataModel().getName().equals(name));
    }

    // 장바구니의 총합 금액 계산
    public double calculateTotalPrice() {
        return items.stream()
                .mapToDouble(item -> item.getDataModel().getPrice() * item.getQuantity())
                .sum();
    }

    // equals 및 hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(username, cart.username); // userId -> username 변경
    }

    @Override
    public int hashCode() {
        return Objects.hash(username); // userId -> username 변경
    }

    // toString
    @Override
    public String toString() {
        return "Cart{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", items=" + items +
                '}';
    }
}