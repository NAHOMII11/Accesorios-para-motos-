package com.reto.orders_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {

    @NotNull(message = "productId es requerido")
    private Long productId;

    @NotNull(message = "quantity es requerido")
    @Min(value = 1, message = "quantity debe ser al menos 1")
    private Integer quantity;
}
