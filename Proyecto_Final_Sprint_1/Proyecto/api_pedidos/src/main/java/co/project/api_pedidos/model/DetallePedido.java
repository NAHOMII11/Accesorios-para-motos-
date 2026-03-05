package co.project.api_pedidos.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @Column(name = "id_producto", nullable = false)
    private Integer idProducto;

    @Column(name = "cantidad", nullable = false) 
    private Integer cantidad;

    @Column(name = "pedido_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal pedidoUnitario;

    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)  
    private BigDecimal subtotal;

    // Getters y Setters
    public Integer getIdDetalle() { return idDetalle; }
    public void setIdDetalle(Integer idDetalle) { this.idDetalle = idDetalle; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPedidoUnitario() { return pedidoUnitario; }
    public void setPedidoUnitario(BigDecimal pedidoUnitario) { this.pedidoUnitario = pedidoUnitario; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}