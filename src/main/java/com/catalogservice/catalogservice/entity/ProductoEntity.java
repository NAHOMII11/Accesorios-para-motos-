package com.catalogservice.catalogservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "productos")
@Data // Si no usas Lombok, genera Getters/Setters manuales como en la guía [cite: 402]
public class ProductoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; [cite: 73]

    @Column(nullable = false)
    private String nombre; [cite: 73]

    @Column(unique = true, nullable = false)
    private String sku; [cite: 73]

    @Column(nullable = false)
    private Integer stockDisponible; [cite: 73]
}