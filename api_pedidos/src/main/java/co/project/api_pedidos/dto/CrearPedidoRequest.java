package co.project.api_pedidos.dto;

import lombok.Data;

@Data
public class CrearPedidoRequest { // se implementa para: HU5
    private Long productoId; // se implementa para: HU5
    private int cantidad; // se implementa para: HU5
}