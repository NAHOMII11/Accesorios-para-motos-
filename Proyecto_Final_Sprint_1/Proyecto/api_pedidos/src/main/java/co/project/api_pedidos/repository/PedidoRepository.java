package co.project.api_pedidos.repository;

import co.project.api_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    
  
    List<Pedido> findByIdCliente(Integer idCliente);
    
  
    List<Pedido> findByEstado_IdEstado(Integer idEstado);
}