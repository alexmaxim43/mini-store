package com.example.store.entity;

import com.example.store.exception.InsufficientStockException;
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
    private String description;
    
    protected Product() {}

    public Product(String name, BigDecimal price, int stock, String description) {
        changeName(name);
        changePrice(price);
        addStock(stock);
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

    public String getDescription() {
        return description;
    }

    public void changeName(String name) {
        if (name == null || name.isBlank()) {
           throw new IllegalArgumentException("Name cannot be blank");
        }
        this.name = name;
    }

    public void changePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }

        this.price = price;
    }

    public void addStock(int stock) {
        if (stock > 0) {
            this.stock += stock;
        }
        else {
            throw new IllegalArgumentException("Stock must be greater than zero");
        }
    }

    public void removeStock(int stock) {
       if (stock <= 0) {
           throw new IllegalArgumentException("Stock must be greater than zero");
       }

       if(stock > this.stock) {
            throw new InsufficientStockException();
       }
       this.stock -= stock;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}