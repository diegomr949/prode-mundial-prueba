# ═══════════════════════════════════════════════════════════
# Dockerfile — Prode Mundial 2026 · CPCE Mendoza
#
# Build en 2 etapas:
#   1. maven:3.9-eclipse-temurin-21 → compila y genera el .jar
#   2. eclipse-temurin:21-jre-jammy → imagen final liviana (~200MB)
#
# Render detecta el Dockerfile automáticamente.
# No hace falta configurar Build Command ni Start Command.
# ═══════════════════════════════════════════════════════════

# ── ETAPA 1: compilar ────────────────────────────────────
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar pom.xml primero para cachear dependencias
# (si solo cambia código, Maven no re-descarga todo)
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Copiar el código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests -q

# ── ETAPA 2: imagen final ────────────────────────────────
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Usuario no-root por seguridad
RUN groupadd -r prode && useradd -r -g prode prode
USER prode

# Copiar solo el .jar generado
COPY --from=build /app/target/prode-mundial-1.0.0.jar app.jar

# Flags de JVM para el plan free de Render (512MB RAM):
# · UseContainerSupport: respeta el límite de RAM del container
# · MaxRAMPercentage:    usa hasta el 75% del heap (384MB de 512)
# · TieredCompilation con nivel 1: arranque más rápido
ENTRYPOINT ["java", \
  "-XX:+UseContainerSupport", \
  "-XX:MaxRAMPercentage=75.0", \
  "-XX:TieredStopAtLevel=1", \
  "-Djava.security.egd=file:/dev/./urandom", \
  "-jar", "app.jar"]

EXPOSE 8080