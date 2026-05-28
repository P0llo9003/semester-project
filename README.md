Debe iniciar cada servicio a través de distintas terminales (New Terminal), ingresando a la carpeta correspondiente y ejecutando:

./mvnw spring-boot:run

Debe iniciar primero:
- api-gateway

Y luego los siguientes microservicios:
- usuarios-service
- peliculas-service
- cines-service
- salas-service
- funciones-service

El API Gateway se encuentra configurado en el puerto 9090.

Las pruebas pueden realizarse en Postman utilizando:
http://localhost:9090

Los endpoints disponibles son:
- /usuarios
- /peliculas
- /cines
- /salas
- /funciones
- /entradas

Todos los microservicios cuentan con operaciones:
- GET
- POST
- PUT
- DELETE

Algunos microservicios también cuentan con métodos GET adicionales con filtros específicos.
