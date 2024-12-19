package com.example.redunm.modellist;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "data_model") // MongoDB 컬렉션 이름
public class DataModel {

    @Id
    private String id; // MongoDB에서 기본 키는 String 타입으로 설정

    private String name;
    private String tag;
    private int price;

    public DataModel() {}

    public DataModel(String name, String tag, int price) {
        this.name = name;
        this.tag = tag;
        this.price = price;
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
}
