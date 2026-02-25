package com.reto.orders_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    @NotNull(message = "userId es requerido")
    private Long userId;

    @NotEmpty(message = "El pedido debe tener al menos un ítem")
    @Valid
    private List<OrderItemRequest> items;
}
