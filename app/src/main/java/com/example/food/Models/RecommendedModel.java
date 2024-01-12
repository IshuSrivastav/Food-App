package com.example.food.Models;

import java.io.Serializable;

public class RecommendedModel implements Serializable {

    String img_url;
    String name;
    int price;
    String rating;
    String description;

    public RecommendedModel() {
    }

    public RecommendedModel(String img_url, String name, int price, String rating, String description) {
        this.img_url = img_url;
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
