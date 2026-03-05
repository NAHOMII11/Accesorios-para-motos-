package co.project.api_pedidos.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "estado")
public class EstadoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Integer idEstado;  

    @Column(name = "nombre_estado", nullable = false, length = 50)
    private String nombreEstado;

    @Column(name = "descripcion", length = 255) 
    private String descripcion;

    @OneToMany(mappedBy = "estado")
    private List<Pedido> pedidos;

    // Constructores
    public EstadoPedido() {}

    public EstadoPedido(Integer idEstado, String nombreEstado, String descripcion) {
        this.idEstado = idEstado;
        this.nombreEstado = nombreEstado;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Integer getIdEstado() { return idEstado; }
    public void setIdEstado(Integer idEstado) { this.idEstado = idEstado; }

    public String getNombreEstado() { return nombreEstado; }
    public void setNombreEstado(String nombreEstado) { this.nombreEstado = nombreEstado; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<Pedido> getPedidos() { return pedidos; }
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }
}