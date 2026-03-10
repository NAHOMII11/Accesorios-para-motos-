package co.project.api_pedidos.exception;

public class StockInsuficienteException extends RuntimeException {

    public StockInsuficienteException(Long productoId, int cantidadSolicitada) {
        super("Stock insuficiente para producto id: " + productoId
                + ", cantidad solicitada: " + cantidadSolicitada);
    }
}