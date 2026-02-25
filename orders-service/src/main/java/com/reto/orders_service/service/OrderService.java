package com.reto.orders_service.service;

import com.reto.orders_service.dto.CreateOrderRequest;
import com.reto.orders_service.dto.OrderItemResponse;
import com.reto.orders_service.dto.OrderResponse;
import com.reto.orders_service.entity.Order;
import com.reto.orders_service.entity.OrderItem;
import com.reto.orders_service.entity.OrderStatus;
import com.reto.orders_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * Crea un pedido, persiste en OrdersDB con estado CREATED (Sprint 1 - HU4 / DDS-28).
     */
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        Order order = Order.builder()
                .userId(request.getUserId())
                .status(OrderStatus.CREATED)
                .totalAmount(BigDecimal.ZERO)
                .build();

        BigDecimal total = BigDecimal.ZERO;
        for (var itemReq : request.getItems()) {
            // Por ahora unitPrice en 0; luego se puede obtener de Catalog si exponen precio
            BigDecimal unitPrice = BigDecimal.ZERO;
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            total = total.add(lineTotal);

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .productId(itemReq.getProductId())
                    .quantity(itemReq.getQuantity())
                    .unitPrice(unitPrice)
                    .build();
            order.getItems().add(item);
        }
        order.setTotalAmount(total);

        order = orderRepository.save(order);
        log.info("Pedido creado: id={}, userId={}", order.getId(), order.getUserId());
        return toResponse(order);
    }

    public List<OrderResponse> findByUserId(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private OrderResponse toResponse(Order order) {
        List<OrderItemResponse> items = order.getItems().stream()
                .map(i -> OrderItemResponse.builder()
                        .productId(i.getProductId())
                        .quantity(i.getQuantity())
                        .unitPrice(i.getUnitPrice())
                        .build())
                .collect(Collectors.toList());
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .status(order.getStatus())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .items(items)
                .build();
    }
}
