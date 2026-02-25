# Orders Service – DDS-28 API Orders Crear Pedido (Sprint 1)

Microservicio de pedidos del reto. Puerto **8083**.

## Criterios de aceptación Sprint 1 (HU4 / DDS-28)

- [x] Persiste en OrdersDB
- [x] Estado inicial **CREATED**
- [x] Responde **201** al crear


## Endpoints (solo tu tarea DDS-28)

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | /ping | Health rápido ("order ok") |
| POST | / o "" | Crear pedido (cuerpo: userId, items[]) |

Vía **Gateway** (puerto 8080): `POST http://localhost:8080/orders`.  
(Consultar pedidos es DDS-30, lo hace otro compañero.)

## Crear pedido – cuerpo del request

```json
{
  "userId": 1,
  "items": [
    { "productId": 1, "quantity": 2 },
    { "productId": 2, "quantity": 1 }
  ]
}
```

## Cómo probar en Postman

1. **Levantar la infra:** en la raíz del proyecto ejecuta `docker-compose up -d` (PostgreSQL).
2. **Levantar Orders Service:** en la carpeta `orders-service` arranca la app (Run en el IDE o `.\mvnw.cmd spring-boot:run`).
3. **Abrir Postman** e importar la colección: `postman/Reto-Microservicios.postman_collection.json` (Import → Upload Files → elegir ese archivo).
4. **Probar Crear Pedido:**
   - **Opción A – Directo al servicio:** en la colección abre **"Orders Service (directo)"** → **"Crear Pedido directo 8083"**. Pulsa **Send**. Debe responder **201** y un JSON con el pedido creado (id, userId, status: CREATED, items, totalAmount, createdAt).
   - **Opción B – Vía Gateway:** primero arranca el **api-gateway** (puerto 8080). Luego en Postman usa **"API Gateway"** → **"Crear Pedido (POST /orders)"** → **Send**. Misma respuesta 201.

**Cuerpo de ejemplo** (ya viene en la petición):  
`{"userId": 1, "items": [{"productId": 1, "quantity": 2}, {"productId": 2, "quantity": 1}]}`

Si algo falla: revisa que PostgreSQL esté arriba (puerto 5432), que la BD `ordersdb` exista (o que el usuario `reto` pueda crearla) y que el servicio esté en 8083.

## Health

- `GET http://localhost:8083/actuator/health`
