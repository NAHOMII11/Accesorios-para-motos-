package com.catalogservice.catalogservice.controller;

public record ProductoRequest(
        @NotBlank String nombre,
        @NotBlank String sku,
        @Min(0) Integer stockDisponible
) {} [cite: 480-490]