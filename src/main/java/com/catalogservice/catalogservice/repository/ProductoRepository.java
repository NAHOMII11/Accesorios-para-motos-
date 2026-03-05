package com.catalogservice.catalogservice.repository;

import com.catalogservice.catalogservice.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {
    boolean existsBySku(String sku); [cite: 467]
}
