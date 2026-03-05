package co.project.api_pedidos.controller;

import co.project.api_pedidos.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// ===== AGREGADO POR NAHOMI 
import co.project.api_pedidos.model.Pedido;
import java.util.List;
import java.util.Optional;
// ===== FIN AGREGADO =====

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<String> crearPedido(
            @RequestParam Long productoId,
            @RequestParam int cantidad) {
        
        pedidoService.crearPedido(productoId, cantidad);
        
        return ResponseEntity.ok("Pedido creado. Mensaje enviado a RabbitMQ.");
    }
    
    // ==========================================================
    // AGREGADO POR NAHOMI  – Sprint 2
    // ==========================================================
    
    /**
     * ENDPOINT: GET /api/pedidos/{id}
     * Obtiene un pedido por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable Integer id) {
        Optional<Pedido> pedido = pedidoService.obtenerPedidoPorId(id);
        return pedido.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }

    /**
     * ENDPOINT: GET /api/pedidos/usuario/{usuarioId}
     * Obtiene todos los pedidos de un cliente
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Pedido>> obtenerPedidosPorUsuario(@PathVariable Integer usuarioId) {
        List<Pedido> pedidos = pedidoService.obtenerPedidosPorCliente(usuarioId);
        return ResponseEntity.ok(pedidos);
    }

    /**
     * ENDPOINT: POST /api/pedidos/crear-completo
    
     */
    @PostMapping("/crear-completo")
    public ResponseEntity<String> crearPedidoCompleto(
            @RequestParam Long productoId,
            @RequestParam int cantidad,
            @RequestParam Integer idCliente,
            @RequestParam String direccionEnvio) {
        
        Pedido pedido = pedidoService.crearPedidoConPersistencia(productoId, cantidad, idCliente, direccionEnvio);
        return ResponseEntity.ok("✅ Pedido creado con persistencia. ID: " + pedido.getIdPedido());
    }
    // ==========================================================
}

