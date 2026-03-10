package co.project.api_pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoEvent {

    private String eventId;
    private String correlationId;
    private Long productoId;
    private Integer cantidad;
}
