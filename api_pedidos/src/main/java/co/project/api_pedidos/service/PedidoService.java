package co.project.api_pedidos.service;

import co.project.api_pedidos.config.RabbitMQConfig;
import co.project.api_pedidos.dto.CheckStockResponse;
import co.project.api_pedidos.dto.PedidoEvent;
import co.project.api_pedidos.entity.Pedido;
import co.project.api_pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoService {

    private final RabbitTemplate rabbitTemplate;
    private final PedidoRepository pedidoRepository;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Value("${catalogo.service.url}")
    private String catalogoServiceUrl;

    public Pedido crearPedido(Long productoId, int cantidad, String correlationId) {

        // 1. Llamar a check-stock propagando CorrelationId
        String url = catalogoServiceUrl + "/api/catalogo/productos/" + productoId + "/check-stock?cantidad=" + cantidad;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Correlation-Id", correlationId);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<CheckStockResponse> stockResp = restTemplate.exchange(
                url, HttpMethod.GET, entity, CheckStockResponse.class);

        CheckStockResponse stock = stockResp.getBody();
        log.info("check-stock productoId={} disponible={} correlationId={}", productoId, stock.getDisponible(), correlationId);

        // 2. Si no hay stock lanzar excepcion -> Controller responde 409
        if (stock == null || !stock.getDisponible()) {
            throw new RuntimeException("Stock insuficiente para productoId=" + productoId);
        }

        // 3. Si hay stock crear y guardar pedido
        Pedido pedido = new Pedido();
        pedido.setProductoId(productoId);
        pedido.setCantidad(cantidad);
        pedido.setEstado("CREATED");
        pedido.setCorrelationId(correlationId);
        pedido.setCreadoEn(LocalDateTime.now());
        pedido.setActualizadoEn(LocalDateTime.now());
        Pedido saved = pedidoRepository.save(pedido);

        // 4. Publicar evento order.created
        String eventId = UUID.randomUUID().toString();
        String mensaje = objectMapper.writeValueAsString(
                new PedidoEvent(eventId, correlationId, productoId, cantidad));

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY_PEDIDO_CREADO,
                mensaje);

        log.info("Pedido creado id={} correlationId={} y mensaje publicado: {}",
                saved.getId(), correlationId, mensaje);
        return saved;
    }

    public Pedido cancelarPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + pedidoId));

        if ("CANCELADO".equals(pedido.getEstado())) {
            throw new RuntimeException("El pedido id: " + pedidoId + " ya fue cancelado anteriormente");
        }

        pedido.setEstado("CANCELADO");
        pedido.setActualizadoEn(LocalDateTime.now());
        pedidoRepository.save(pedido);

        String eventId = UUID.randomUUID().toString();
        String correlationId = pedido.getCorrelationId() != null
                ? pedido.getCorrelationId()
                : UUID.randomUUID().toString();
        String mensaje = objectMapper.writeValueAsString(
                new PedidoEvent(eventId, correlationId, pedido.getProductoId(), pedido.getCantidad()));

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY_PEDIDO_CANCELADO,
                mensaje);

        log.info("Pedido id={} cancelado correlationId={} y mensaje publicado: {}",
                pedidoId, correlationId, mensaje);
        return pedido;
    }
}