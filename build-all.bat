rem $env:DOCKER_BUILDKIT=0
rem docker rm -f $(docker ps -aq)
FOR /f %%i IN ('docker ps -aq') DO docker rm -f %%i
FOR /f %%i IN ('docker images -aq') DO docker rmi -f %%i
cd api-gateway
call .\mvnw clean package -DskipTests

cd ../cines-service
call .\mvnw clean package -DskipTests

cd ../funciones-service
call  .\mvnw clean package -DskipTests

cd ../peliculas-service
call .\mvnw clean package -DskipTests

cd ../salas-service
call .\mvnw clean package -DskipTests

cd ../usuarios-service
call .\mvnw clean package -DskipTests

cd ../confiteria-service
call .\mvnw clean package -DskipTests

cd ../pago-service
call .\mvnw clean package -DskipTests

cd ../fidelizacion-service
call .\mvnw clean package -DskipTests

cd ../notificacion-service
call .\mvnw clean package -DskipTests

cd ../empleados-service
call .\mvnw clean package -DskipTests

