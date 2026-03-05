package co.project.api_pedidos.service;

import co.project.api_pedidos.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
// ===== AGREGADO POR NAHOMI =====
import co.project.api_pedidos.model.Pedido;
import co.project.api_pedidos.model.DetallePedido;
import co.project.api_pedidos.model.EstadoPedido;
import co.project.api_pedidos.repository.PedidoRepository;
import co.project.api_pedidos.repository.EstadoPedidoRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
// ===============================

@Service
public class PedidoService {

    private final RabbitTemplate rabbitTemplate;

    private final PedidoRepository pedidoRepository;  //  AGREGADO Nahomi
    private final EstadoPedidoRepository estadoPedidoRepository;   //  AGREGADO Nahomi

    public PedidoService(RabbitTemplate rabbitTemplate, PedidoRepository pedidoRepository, EstadoPedidoRepository estadoPedidoRepository)  { //  AGREGADO  Nahomi PedidoRepository pedidoRepository, EstadoPedidoRepository estadoPedidoRepository
        this.rabbitTemplate = rabbitTemplate;
        this.pedidoRepository = pedidoRepository; // AGREGADO  Nahomi
        this.estadoPedidoRepository = estadoPedidoRepository; // AGREGADO  Nahomi
    }

    public void crearPedido(Long productoId, int cantidad) {
        // 1. Guardar pedido en BD (tu lógica)
        
        // 2. Publicar evento para descontar stock
        String mensaje = String.format("{\"productoId\": %d, \"cantidad\": %d}", productoId, cantidad);
        
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE,
            RabbitMQConfig.ROUTING_KEY_PEDIDO_CREADO,
            mensaje
        );
        
        System.out.println("Mensaje publicado: " + mensaje);
    } 
    // ==========================================================
    //  CÓDIGO AGREGADO POR NAHOMI  – Sprint 2
    // ==========================================================
    
     public Pedido crearPedidoConPersistencia(Long productoId, int cantidad, Integer idCliente, String direccionEnvio) {
        
        // Crear entidad Pedido
        Pedido pedido = new Pedido();
        pedido.setIdCliente(idCliente);
        pedido.setDireccionEnvio(direccionEnvio);
        pedido.setCantidad(cantidad);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setFechaActualizacion(LocalDateTime.now());
        
        // Crear detalle del pedido
        DetallePedido detalle = new DetallePedido();
        detalle.setIdProducto(productoId.intValue());
        detalle.setCantidad(cantidad);
        detalle.setPedidoUnitario(BigDecimal.valueOf(75.25));
        detalle.setSubtotal(detalle.getPedidoUnitario().multiply(BigDecimal.valueOf(cantidad)));
        
        // Agregar detalle al pedido
        pedido.agregarDetalle(detalle);
        
        //  ASIGNAR ESTADO "CREADO" 
        EstadoPedido estadoCreado = estadoPedidoRepository.findByNombreEstado("CREADO")
            .orElseThrow(() -> new RuntimeException("Estado CREADO no encontrado en BD"));
        pedido.setEstado(estadoCreado);
     
        
        // GUARDAR EN BASE DE DATOS
        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        
        // Publicar evento en RabbitMQ
        String mensaje = String.format("{\"productoId\": %d, \"cantidad\": %d}", productoId, cantidad);
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE,
            RabbitMQConfig.ROUTING_KEY_PEDIDO_CREADO,
            mensaje
        );
        
        System.out.println("✅ Pedido guardado con ID: " + pedidoGuardado.getIdPedido());
        
        return pedidoGuardado;
    }

    public Optional<Pedido> obtenerPedidoPorId(Integer id) {
        return pedidoRepository.findById(id);
    }

    public List<Pedido> obtenerPedidosPorCliente(Integer idCliente) {
        return pedidoRepository.findByIdCliente(idCliente);
    }
}
