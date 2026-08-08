package com.example.store.mapper;

import com.example.store.dto.product.CreateProductRequest;
import com.example.store.dto.product.ProductResponse;
import com.example.store.entity.Product;

public class ProductMapper {
    public static Product toEntity(CreateProductRequest request) {
        return new Product(request.name(), request.price(), request.stock(), request.sku(), request.description());
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getName(), product.getPrice(), product.getSku(), product.getDescription());
    }
}
