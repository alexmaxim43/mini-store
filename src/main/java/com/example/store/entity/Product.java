package com.example.store.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private int stock;
    @Column(nullable = false, unique = true)
    private String sku;
    private String description;

    protected Product() {
    }

    public Product(String name, BigDecimal price, int stock, String sku, String description) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }

        if (stock <= 0) {
            throw new IllegalArgumentException("Stock must be greater than zero");
        }

        this.name = name;
        changePrice(price);
        this.stock = stock;
        this.sku = sku;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getSku() {
        return sku;
    }

    public String getDescription() {
        return description;
    }

    public void changePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }

        this.price = price;
    }
}