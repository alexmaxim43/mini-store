package com.example.store.controller;

import com.example.store.dto.product.ChangePriceRequest;
import com.example.store.dto.product.CreateProductRequest;
import com.example.store.dto.product.ProductResponse;
import com.example.store.mapper.ProductMapper;
import com.example.store.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{sku}")
    public ProductResponse getProductBySku(@PathVariable String sku) {
        return productService.getProductBySku(sku);
    }

    @GetMapping
    public Page<ProductResponse> getAllProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return productService.getAllProducts(page, size);
    }

    @PostMapping
    public ProductResponse addProduct(@RequestBody @Valid CreateProductRequest createProductRequest) {
        return productService.createProduct(createProductRequest);
    }

    @PatchMapping("/{sku}/price")
    public ProductResponse changePrice(@PathVariable String sku, @RequestBody @Valid ChangePriceRequest changePriceRequest) {
        return productService.changePrice(sku, changePriceRequest);

    }

    @DeleteMapping("/{sku}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String sku) {
        productService.deleteProduct(sku);
    }
}
