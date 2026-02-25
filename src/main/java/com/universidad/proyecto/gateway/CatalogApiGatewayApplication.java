package com.universidad.proyecto.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatalogApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogApiGatewayApplication.class, args);
        System.out.println("🚀 Gateway del Catálogo corriendo en el puerto 8080");
    }
}