package co.project.api_catalogo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

@RestController
@RequestMapping("/api/catalogo")
public class StockController {

    private final DataSource dataSource;

    public StockController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/check-stock/{productoId}")
    public ResponseEntity<?> checkStock(
            @PathVariable Long productoId,
            @RequestParam int cantidad) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                "SELECT stock_disponible FROM inventario WHERE product_id = ?")) {
            ps.setLong(1, productoId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int stock = rs.getInt("stock_disponible");
                boolean disponible = stock >= cantidad;
                return ResponseEntity.ok(Map.of(
                    "productoId", productoId,
                    "stockDisponible", stock,
                    "cantidadSolicitada", cantidad,
                    "disponible", disponible
                ));
            } else {
                return ResponseEntity.status(404)
                    .body(Map.of("error", "Producto no encontrado en inventario"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(Map.of("error", e.getMessage()));
        }
    }
}