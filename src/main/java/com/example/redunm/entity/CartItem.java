package com.example.redunm.entity;

import com.example.redunm.modellist.DataModel;

public class CartItem {
    private DataModel dataModel;
    private int quantity;

    public CartItem(DataModel dataModel) {
        this.dataModel = dataModel;
        this.quantity = 1;
    }

    public DataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

    public int getQuantity() {
        return quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    public void decrementQuantity() {
        this.quantity--;
    }
}

