# pago-service
Microservicio encargado de registrar pagos asociados a compras y ventas. Valida la existencia de la compra y venta, registra el método de pago y genera una referencia.
## Puerto
```
8084
```
## Tecnologías
- Java 21
- Spring Boot
- Spring Data JPA
- MySQL
- Eureka Client
- Swagger/OpenAPI
- HATEOAS
- Mockito/JUnit
- Docker
- Railway

## Base de datos
```
db_minimarket
```
## Endpoints V1
```
GET /api/v1/pagos
GET /api/v1/pagos/{id}
POST /api/v1/pagos
DELETE /api/v1/pagos/{id}
```
## Endpoints V2 HATEOAS
```
GET /api/v2/pagos
GET /api/v2/pagos/{id}
POST /api/v2/pagos
PUT /api/v2/pagos/{id}
DELETE /api/v2/pagos/{id}
```
## Swagger
```
http://localhost:8084/swagger-ui.html
```
## Ejemplo JSON
```json
{
  "compraId": 1,
  "ventaId": 1,
  "monto": 15990,
  "metodo": "EFECTIVO"
}
```
## Ejecutar pruebas
```bash
mvn test
```
## Ejecutar localmente
```bash
mvn spring-boot:run
```
## Configuración Railway
```properties
server.port=${PORT:8084}
```
Variables recomendadas:

```properties
SPRING_DATASOURCE_URL=jdbc:mysql://HOST:PORT/railway?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false
SPRING_DATASOURCE_USERNAME=TU_USUARIO
SPRING_DATASOURCE_PASSWORD=TU_PASSWORD
EUREKA_CLIENT_ENABLED=false
```
