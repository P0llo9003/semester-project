
## Descripción de Proyecto

Proyecto desarrollado bajo una arquitectura de microservicios para la administración de un sistema de cine. Permite gestionar usuarios, películas, cines, salas, funciones, entradas, productos de confitería y pagos. Cada microservicio posee su propia base de datos y se comunica con otros servicios mediante WebClient. El acceso a los servicios se realiza de forma centralizada a través de API Gateway.



## Lista de Microservicios

| Microservicio     | Puerto  |
|-------------------|---------|
| usuarios-service  | 9091    |
| peliculas-service | 9092    |
| cines-service     | 9093    |
| salas-service     | 9094    |
| funciones-service | 9095    |
| confiteria-service| 9060    |
| pago-service      | 9097    |



## Instrucciones de Ejecución:

1. Abrir aplicacion **XAMPP** y levantar **Apache** y **MySQL**
2. Abrir una terminal dentro de la carpeta raiz del proyecto y ejecutar **.\build-all.bat**. Esperar a que finalice la compilacion de los archivos **.jar**
3. Una vez completada la compilacion, abrir la aplicacion **Docker Desktop** y ejecutar en la terminal desde la carpeta raiz del proyecto: **docker-compose up**
4. Esperar a que Docker termine de crear e iniciar todos los contenedores del sistema.
5. Verificar que todos los microservicios se encuentren en estado Running dentro de Docker.
6. Una vez iniciado el sistema, las pruebas pueden realizarse mediante Postman utilizando el API Gateway: **http://localhost:9090/**

## Endpoints Disponibles:

- /usuarios
- /peliculas
- /cines
- /salas
- /funciones
- /entradas
- /confiteria
- /pagos



Todos los microservicios cuentan con operaciones:
- GET
- GET por ID
- POST
- PUT
- DELETE

Además, algunos microservicios incluyen búsquedas y filtros específicos.
Más informacion sobre los endpoint en los enlaces Swagger:

## Enlaces Swagger

Usuarios Service:
http://localhost:9091/doc/swagger-ui/index.html#/

Películas Service:
http://localhost:9092/doc/swagger-ui/index.html#/

Cines Service:
http://localhost:9093/doc/swagger-ui/index.html#/

Salas Service:
http://localhost:9094/doc/swagger-ui/index.html#/

Funciones Service:
http://localhost:9095/doc/swagger-ui/index.html#/

Confiteria Service:
http://localhost:9060/doc/swagger-ui/index.html#/

Pago Service:
http://localhost:9097/doc/swagger-ui/index.html#/

Fidelización Service:
http://localhost:9096/swagger-ui/index.html

Notificación Service:
http://localhost:9098/swagger-ui/index.html

Empleados Service:
http://localhost:9099/swagger-ui/index.html


## Consideraciones

El microservicio pago-service genera registros de prueba automáticamente al iniciar.
Si el sistema se detiene y se vuelve a levantar utilizando bases de datos previamente modificadas, puede ser necesario limpiar las tablas o recrear las bases de datos para evitar inconsistencias entre los datos generados y los registros existentes.


## Integrantes

- Sebastian Gomez
- Aidmith Ayala