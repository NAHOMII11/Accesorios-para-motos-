Accesorios para Motos - Microservicios

Proyecto académico de arquitectura de microservicios para la gestión de pedidos de accesorios para motos. Implementa los principios de Sprint 1 – Arquitectura Base y Seguridad.

 Sprint 1: Arquitectura Base y Seguridad

 Entregables del Sprint

 Infraestructura Base (HU1)

API Gateway funcional como punto único de entrada.

Rutas configuradas y verificadas para los servicios:

auth-service (Autenticación)

catalog-service (Catálogo)

orders-service (Pedidos)

 Autenticación (HU2)

Servicio de autenticación con JWT.

Endpoint /auth/login que genera token firmado con expiración.

Manejo de errores:

401 Unauthorized para credenciales inválidas.

403 Forbidden para accesos no autorizados.

Pruebas con token válido e inválido.
