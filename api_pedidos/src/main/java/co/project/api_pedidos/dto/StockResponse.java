package co.project.api_pedidos.dto;

import lombok.Data;

@Data
public class StockResponse { // se implementa para: HU5
    private Long productoId; // se implementa para: HU5
    private int stockDisponible; // se implementa para: HU5
    private int cantidadSolicitada; // se implementa para: HU5
    private boolean disponible; // se implementa para: HU5
}