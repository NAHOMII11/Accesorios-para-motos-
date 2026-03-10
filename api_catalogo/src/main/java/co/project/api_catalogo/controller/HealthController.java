package co.project.api_catalogo.controller;

import co.project.api_catalogo.dto.CheckStockResponse;
import co.project.api_catalogo.entity.Producto;
import co.project.api_catalogo.service.ProductoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/catalogo")
@RequiredArgsConstructor
public class HealthController {

    private final ProductoService productoService;

    @GetMapping("/health")
    public String health() {
        return "API Catalogo - OK";
    }

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> listarProductos() {
        return ResponseEntity.ok(productoService.listarProductos());
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerProducto(id));
    }

    @PostMapping("/productos")
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crearProducto(producto));
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.actualizarProducto(id, producto));
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/productos/{id}/check-stock")
    public ResponseEntity<CheckStockResponse> checkStock(
            @PathVariable Long id,
            @RequestParam Integer cantidad,
            @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId) {

        log.info("check-stock productoId={} cantidad={} correlationId={}", id, cantidad, correlationId);

        Producto producto = productoService.obtenerProducto(id);
        boolean disponible = producto.getStock() >= cantidad;

        CheckStockResponse response = new CheckStockResponse(
                id,
                producto.getStock(),
                cantidad,
                disponible
        );

        return ResponseEntity.ok(response);
    }
}