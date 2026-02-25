package com.reto.orders_service.controller;

import com.reto.orders_service.dto.CreateOrderRequest;
import com.reto.orders_service.dto.OrderResponse;
import com.reto.orders_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * DDS-28 / HU4 - Crear Pedido (tu tarea Sprint 1).
     * Vía Gateway: POST http://localhost:8080/orders
     * Directo: POST http://localhost:8083/
     */
    @PostMapping({"", "/"})
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        OrderResponse created = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
