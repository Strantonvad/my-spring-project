package ru.geekbrains.persistance;

public class Product {
    private final int id;
    private final String title;
    private final Double cost;

    public Product(int id, String title, Double cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Double getCost() {
        return cost;
    }
}
