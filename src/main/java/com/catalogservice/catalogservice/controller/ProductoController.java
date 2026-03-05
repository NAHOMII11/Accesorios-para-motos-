package com.catalogservice.catalogservice.controller;

@RestController
@RequestMapping("/catalog/productos")
public class ProductoController {
    private final ProductoService service;

    public ProductoController(ProductoService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<?> getAll() { return ResponseEntity.ok(service.listarTodo()); } [cite: 77]

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductoRequest req) {
        return ResponseEntity.status(201).body(service.crear(req));
    } [cite: 76]

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductoRequest req) {
        return ResponseEntity.ok(service.actualizar(id, req));
    } [cite: 81]

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    } [cite: 83]
}