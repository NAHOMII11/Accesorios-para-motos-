package co.project.api_catalogo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckStockResponse {

    private Long productoId;
    private Integer stockDisponible;
    private Integer cantidadSolicitada;
    private Boolean disponible;
}