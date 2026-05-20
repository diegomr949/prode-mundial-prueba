# Prode Mundial 2026 — CPCE Mendoza

Sistema interno de predicciones del Mundial de Fútbol para el
Consejo Profesional de Ciencias Económicas de Mendoza.

---

## Estructura del repositorio

Los archivos del proyecto están distribuidos en tres carpetas
según cuándo fueron generados. Al armar el proyecto en IntelliJ
tenés que copiarlos a la estructura Maven estándar:

```
prode-mundial/                          ← raíz del proyecto Maven
├── pom.xml                             ← de v2/
├── .env.example                        ← de backend-config/
├── .gitignore                          ← de backend-config/
│
└── src/
    ├── main/
    │   ├── java/ar/org/cpcemza/prodemundial/
    │   │   │
    │   │   ├── ProdemundialApplication.java        ← final/
    │   │   │
    │   │   ├── model/
    │   │   │   ├── Usuario.java                    ← final/model/
    │   │   │   ├── Equipo.java                     ← final/model/
    │   │   │   ├── Partido.java                    ← final/model/
    │   │   │   ├── Prediccion.java                 ← final/model/
    │   │   │   ├── Jugador.java                    ← final/model/
    │   │   │   └── EstadisticaEquipo.java           ← final/model/
    │   │   │
    │   │   ├── repository/
    │   │   │   ├── UsuarioRepository.java           ← final/repository/
    │   │   │   ├── PartidoRepository.java           ← final/repository/
    │   │   │   ├── PrediccionRepository.java        ← final/repository/
    │   │   │   ├── EquipoRepository.java            ← v2/repository/
    │   │   │   └── JugadorRepository.java           ← v2/repository/
    │   │   │
    │   │   ├── dto/
    │   │   │   ├── LoginRequestDTO.java             ← final/dto/
    │   │   │   ├── RegistroRequestDTO.java          ← final/dto/
    │   │   │   ├── AuthResponseDTO.java             ← final/dto/
    │   │   │   ├── PartidoResponseDTO.java          ← final/dto/
    │   │   │   ├── PrediccionRequestDTO.java        ← final/dto/
    │   │   │   ├── PrediccionResponseDTO.java       ← final/dto/
    │   │   │   ├── ClasificacionDTO.java            ← final/dto/
    │   │   │   ├── DashboardUsuarioDTO.java         ← final/dto/
    │   │   │   ├── ResetPasswordRequestDTO.java     ← final/dto/
    │   │   │   ├── ResultadoRequestDTO.java         ← final/dto/
    │   │   │   ├── CambiarPasswordRequestDTO.java   ← final/dto/
    │   │   │   ├── EquipoResponseDTO.java           ← v2/dto/
    │   │   │   ├── JugadorRequestDTO.java           ← v2/dto/
    │   │   │   └── JugadorResponseDTO.java          ← v2/dto/
    │   │   │
    │   │   ├── exception/
    │   │   │   ├── ResourceNotFoundException.java   ← final/exception/
    │   │   │   ├── PrediccionCerradaException.java  ← final/exception/
    │   │   │   ├── PrediccionDuplicadaException.java← final/exception/
    │   │   │   └── GlobalExceptionHandler.java      ← backend-config/
    │   │   │
    │   │   ├── config/
    │   │   │   ├── JwtUtil.java                    ← backend-config/
    │   │   │   ├── JwtAuthFilter.java              ← final/config/
    │   │   │   ├── UserDetailsServiceImpl.java     ← final/config/
    │   │   │   ├── SecurityConfig.java             ← v2/config/
    │   │   │   ├── RateLimitFilter.java            ← backend-config/
    │   │   │   └── AuthenticatedUserResolver.java  ← final/config/
    │   │   │
    │   │   ├── service/
    │   │   │   ├── AuthService.java                ← final/service/
    │   │   │   ├── AdminService.java               ← final/service/
    │   │   │   ├── PartidoService.java             ← v2/service/
    │   │   │   ├── PrediccionService.java          ← final/service/
    │   │   │   ├── CalculoPuntosService.java       ← final/service/
    │   │   │   ├── EquipoService.java              ← v2/service/
    │   │   │   └── PerfilService.java              ← v2/service/
    │   │   │
    │   │   └── controller/
    │   │       ├── AuthController.java             ← final/controller/
    │   │       ├── PartidoController.java          ← v2/controller/
    │   │       ├── PrediccionController.java       ← v2/controller/
    │   │       ├── RankingController.java          ← v2/controller/
    │   │       ├── AdminController.java            ← v2/controller/
    │   │       ├── EquipoController.java           ← v2/controller/
    │   │       └── PerfilController.java           ← v2/controller/
    │   │
    │   └── resources/
    │       ├── application.yml                     ← final/
    │       └── data.sql                            ← v2/sql/
    │
    └── test/
        ├── java/ar/org/cpcemza/prodemundial/
        │   ├── CalculoPuntosServiceTest.java       ← v2/test/
        │   └── PrediccionServiceTest.java          ← v2/test/
        └── resources/
            └── application-test.yml               ← final/
```

---

## Primer deploy — paso a paso

### 1. Clonar y configurar entorno

```bash
# Copiar plantilla de variables de entorno
cp .env.example .env

# Generar el JWT secret (mínimo 64 caracteres)
openssl rand -hex 64

# Editar .env con los valores reales
nano .env
```

### 2. Variables de entorno obligatorias

| Variable | Descripción | Ejemplo |
|---|---|---|
| `DB_URL` | URL de conexión a Neon/Supabase | `jdbc:postgresql://...?sslmode=require` |
| `DB_USERNAME` | Usuario de la BD | `neondb_owner` |
| `DB_PASSWORD` | Contraseña de la BD | `••••••••` |
| `JWT_SECRET` | Clave secreta JWT ≥ 64 chars | resultado de `openssl rand -hex 64` |
| `JWT_EXPIRATION_MS` | Duración del token (ms) | `86400000` (24h) |
| `CORS_ALLOWED_ORIGINS` | URL exacta del frontend | `https://prode.pages.dev` |
| `HIBERNATE_DDL_AUTO` | `update` (1er deploy) → `validate` (producción) | `validate` |
| `SQL_INIT_MODE` | `always` (1er deploy) → `never` | `never` |

### 3. Primera ejecución local

```bash
# Compilar y ejecutar tests
mvn clean verify

# Arrancar en desarrollo
mvn spring-boot:run \
  -DHIBERNATE_DDL_AUTO=update \
  -DSQL_INIT_MODE=always \
  -DDB_URL=jdbc:postgresql://localhost:5432/prode_db \
  -DDB_USERNAME=postgres \
  -DDB_PASSWORD=postgres \
  -DJWT_SECRET="tu-secret-de-64-chars-aqui..." \
  -DCORS_ALLOWED_ORIGINS=http://localhost:5500
```

### 4. Cargar plantillas de jugadores (opcional)

Después del primer arranque exitoso, ejecutar en el SQL editor de Neon:

```sql
-- Cargar las plantillas de los 48 equipos
\i v2/sql/jugadores_data.sql
```

O copiarlo directamente en el SQL Editor de neon.tech.

### 5. Deploy en Render

1. Conectar repositorio GitHub en render.com → New Web Service
2. Build Command: `mvn clean package -DskipTests`
3. Start Command: `java -jar target/prode-mundial-1.0.0.jar`
4. Cargar todas las variables de entorno del paso 2
5. En el primer deploy: `HIBERNATE_DDL_AUTO=update` y `SQL_INIT_MODE=always`
6. Después del primer deploy exitoso: cambiar a `validate` y `never`

### 6. Deploy del frontend en Cloudflare Pages

1. dash.cloudflare.com → Pages → Connect to Git
2. Framework preset: None
3. Build command: (vacío)
4. Build output: `/`
5. Después del deploy, editar `index.html`:
   ```html
   <meta name="api-base" content="https://prode-mundial.onrender.com/api" />
   ```
6. Actualizar `CORS_ALLOWED_ORIGINS` en Render con la URL de Cloudflare

---

## Endpoints disponibles

```
POST   /api/auth/login
POST   /api/auth/registro

GET    /api/partidos
GET    /api/partidos?estado={PENDIENTE|EN_JUEGO|FINALIZADO}
GET    /api/partidos/{id}
GET    /api/predicciones/mis-predicciones
POST   /api/predicciones
GET    /api/ranking
GET    /api/equipos
GET    /api/equipos/{id}
GET    /api/equipos/{id}/jugadores
PUT    /api/perfil/cambiar-password

GET    /api/admin/usuarios
GET    /api/admin/usuarios/{id}/dashboard
PUT    /api/admin/usuarios/{id}/reset-password
PUT    /api/admin/partidos/{id}/resultado
GET    /api/admin/clasificacion
POST   /api/admin/equipos/{id}/jugadores
DELETE /api/admin/jugadores/{id}
```

---

## Credenciales iniciales del admin

```
Email:    admin@cpce.org.ar
Password: Admin2026!
```

⚠️ **Cambiar la contraseña en el primer login usando el panel admin.**

---

## Correr los tests

```bash
mvn test
```

Los tests usan H2 en memoria (no necesitan PostgreSQL).
Cobertura incluida: motor de puntos (15 combinaciones) y bloqueo de predicciones.
