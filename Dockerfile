# Paso 1: Compilar la aplicación usando Maven y Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Forzar a la JVM de Maven a habilitar los procesadores de anotaciones (Lombok)
ENV MAVEN_OPTS="-Dmaven.compiler.proc=full"

# Copiamos el pom.xml primero para aprovechar la caché
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos el código fuente y compilamos
COPY src ./src
RUN mvn clean package -DskipTests

# Paso 2: Crear la imagen final de producción, ultra liviana
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/prode-mundial-1.0.0.jar app.jar

# Configuración de memoria optimizada para la capa gratuita de Render
ENV JAVA_OPTS="-Xmx384m -Xms384m"

EXPOSE 8080
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "/app/app.jar"]