package com.example.store.dto.product;

import java.math.BigDecimal;

public record ProductResponse(
        String name,
        BigDecimal price,
        String sku,
        String description
) {
}
