package co.project.api_pedidos.repository;

import co.project.api_pedidos.model.EstadoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EstadoPedidoRepository extends JpaRepository<EstadoPedido, Integer> {
    Optional<EstadoPedido> findByNombreEstado(String nombreEstado);
}