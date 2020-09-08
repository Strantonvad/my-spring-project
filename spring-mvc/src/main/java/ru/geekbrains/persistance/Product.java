package ru.geekbrains.persistance;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Product {
    private int id;
    @NotBlank
    private String title;
    @NotNull
    private Double cost;

    public Product() {
    }

    public Product(int id, String title, Double cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
