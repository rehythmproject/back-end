package com.example.redunm.modellist;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "data_model")
public class DataModel {

    @Id
    private String id;
    private String name;
    private String tag;
    private int price;
    private String description;

    public DataModel() {}

    public DataModel(String name, String tag, int price, String description) {
        this.name = name;
        this.tag = tag;
        this.price = price;
        this.description = description;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
