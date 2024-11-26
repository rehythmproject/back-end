package com.example.redunm.entity;

public class CartItem {
    private String modelId;  // 모델 ID
    private String name;     // 모델 이름
    private int price;       // 가격
    private int quantity;    // 수량

    public CartItem() {}

    public CartItem(String modelId, String name, int price, int quantity) {
        this.modelId = modelId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Getter & Setter
    public String getModelId() { return modelId; }
    public void setModelId(String modelId) { this.modelId = modelId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
