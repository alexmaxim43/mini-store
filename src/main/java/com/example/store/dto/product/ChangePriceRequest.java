package com.example.store.dto.product;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ChangePriceRequest(
        @NotNull @Positive BigDecimal price
) {
}

