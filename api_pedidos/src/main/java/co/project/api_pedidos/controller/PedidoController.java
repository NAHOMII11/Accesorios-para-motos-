package co.project.api_pedidos.controller;

import co.project.api_pedidos.dto.CrearPedidoRequest; // se implementa para: HU5
import co.project.api_pedidos.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearPedido(
            @RequestBody CrearPedidoRequest request, // se implementa para: HU5
            @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId) { // se implementa para: HU5
        try {
            pedidoService.crearPedido(request, correlationId); // se implementa para: HU5
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "Pedido creado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT) // se implementa para: HU5
                    .body(Map.of("error", e.getMessage())); // se implementa para: HU5
        }
    }
}