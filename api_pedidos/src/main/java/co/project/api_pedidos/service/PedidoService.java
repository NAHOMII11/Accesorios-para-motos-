package co.project.api_pedidos.service;

import co.project.api_pedidos.config.RabbitMQConfig;
import co.project.api_pedidos.dto.CrearPedidoRequest;
import co.project.api_pedidos.dto.StockResponse; // se implementa para: HU5
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpEntity; // se implementa para: HU5
import org.springframework.http.HttpHeaders; // se implementa para: HU5
import org.springframework.http.HttpMethod; // se implementa para: HU5
import org.springframework.http.ResponseEntity; // se implementa para: HU5
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate; // se implementa para: HU5
import java.util.UUID; // se implementa para: HU5

@Service
public class PedidoService {

    private final RabbitTemplate rabbitTemplate;
    private final RestTemplate restTemplate; // se implementa para: HU5

    private static final String CATALOGO_CHECK_STOCK_URL = "http://api-catalogo:8081/api/catalogo/check-stock/"; // se implementa para: HU5

    public PedidoService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.restTemplate = new RestTemplate(); // se implementa para: HU5
    }

    public void crearPedido(CrearPedidoRequest request, String correlationId) { // se implementa para: HU5

        // Generar CorrelationId si no viene en la petición // se implementa para: HU5
        if (correlationId == null || correlationId.isEmpty()) { // se implementa para: HU5
            correlationId = UUID.randomUUID().toString(); // se implementa para: HU5
        } // se implementa para: HU5

        // Propagar CorrelationId en cabecera HTTP hacia check-stock // se implementa para: HU5
        HttpHeaders headers = new HttpHeaders(); // se implementa para: HU5
        headers.set("X-Correlation-Id", correlationId); // se implementa para: HU5
        HttpEntity<Void> entity = new HttpEntity<>(headers); // se implementa para: HU5

        // Llamar a check-stock del catálogo vía REST // se implementa para: HU5
        String url = CATALOGO_CHECK_STOCK_URL + request.getProductoId() + "?cantidad=" + request.getCantidad(); // se implementa para: HU5
        ResponseEntity<StockResponse> response = restTemplate.exchange( // se implementa para: HU5
            url, HttpMethod.GET, entity, StockResponse.class // se implementa para: HU5
        ); // se implementa para: HU5

        // Verificar disponibilidad de stock // se implementa para: HU5
        StockResponse stockInfo = response.getBody(); // se implementa para: HU5

        // Lanzar excepción si no hay stock — el controller responderá 409 // se implementa para: HU5
        if (!stockInfo.isDisponible()) { // se implementa para: HU5
            throw new RuntimeException("Stock insuficiente para el producto " + request.getProductoId()); // se implementa para: HU5
        } // se implementa para: HU5

        // Publicar evento a RabbitMQ con correlationId incluido
        String mensaje = String.format(
            "{\"productoId\": %d, \"cantidad\": %d, \"correlationId\": \"%s\"}",
            request.getProductoId(), request.getCantidad(), correlationId
        );
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE,
            RabbitMQConfig.ROUTING_KEY_PEDIDO_CREADO,
            mensaje
        );

        System.out.println("[" + correlationId + "] Pedido creado y evento publicado."); // se implementa para: HU5
    }
}