package com.example.store.service;

import com.example.store.entity.Product;
import com.example.store.exception.ProductAlreadyExistsException;
import com.example.store.exception.ProductNotFoundException;
import com.example.store.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductService {
    private static final int MAX_PAGE_SIZE = 100;

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("ID", id.toString()));
    }

    public Product getProductBySku(String sku){
        return productRepository.findBySku(sku).orElseThrow(() -> new ProductNotFoundException("SKU", sku));
    }

    public Page<Product> getAllProducts(int page, int size) {
        size = Math.min(size, MAX_PAGE_SIZE);
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable);
    }

    public Product createProduct(Product product) {
        if (productRepository.existsBySku(product.getSku())) {
            throw new ProductAlreadyExistsException(product.getSku());
        }

        return productRepository.save(product);
    }

    public Product changePrice(String sku, BigDecimal newPrice) {
        Product product = getProductBySku(sku);

        product.changePrice(newPrice);

        return productRepository.save(product);
    }

    public void deleteProduct(String sku){
        Product product = getProductBySku(sku);

        productRepository.delete(product);
    }
}
