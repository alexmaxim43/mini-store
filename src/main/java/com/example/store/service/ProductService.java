package com.example.store.service;

import com.example.store.dto.product.ChangePriceRequest;
import com.example.store.dto.product.CreateProductRequest;
import com.example.store.dto.product.ProductResponse;
import com.example.store.entity.Product;
import com.example.store.exception.ProductAlreadyExistsException;
import com.example.store.exception.ProductNotFoundException;
import com.example.store.mapper.ProductMapper;
import com.example.store.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private static final int MAX_PAGE_SIZE = 100;

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private Product getProductEntityBySku(String sku) {
        return productRepository.findBySku(sku)
                .orElseThrow(() -> new ProductNotFoundException("SKU", sku));
    }

    public ProductResponse getProductBySku(String sku) {
        Product product = getProductEntityBySku(sku);

        return ProductMapper.toResponse(product);
    }

    public Page<ProductResponse> getAllProducts(int page, int size) {
        size = Math.min(size, MAX_PAGE_SIZE);
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable).map(ProductMapper::toResponse);
    }

    public ProductResponse createProduct(CreateProductRequest createProductRequest) {
        if (productRepository.existsBySku(createProductRequest.sku())) {
            throw new ProductAlreadyExistsException(createProductRequest.sku());
        }

        Product product = ProductMapper.toEntity(createProductRequest);
        Product savedProduct = productRepository.save(product);

        return ProductMapper.toResponse(savedProduct);
    }

    public ProductResponse changePrice(String sku, ChangePriceRequest changePriceRequest) {
        Product product = getProductEntityBySku(sku);

        product.changePrice(changePriceRequest.price());

        return ProductMapper.toResponse(productRepository.save(product));
    }

    public void deleteProduct(String sku) {
        Product product = getProductEntityBySku(sku);

        productRepository.delete(product);
    }
}
