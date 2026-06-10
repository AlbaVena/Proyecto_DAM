# GESTIONA

Aplicación de escritorio para la gestión de Formaciones en Empresa del Departamento de Informática del CIFP La Laboral.

Incluye una aplicación móvil Android para el perfil de Estudiante.

## Tecnologías
- Java 23 + Spring Boot 3.4.0
- JavaFX 23 + FXML + Scene Builder
- JPA + Hibernate 6.6.2
- MySQL (XAMPP)
- JaspeSfot Studio 6.20.0
- Kotlin + JetPack Compose + Retrofit (app Android)

## Requisitos previos
- Java 23 instalado
- XAMPP activo
- Eclipse con soporte Maven
- Android Studio (para emular la app móvil)

## Configuración de la base de datos
1. Abrir phpMyAdmin en XAMPP y crear una base de datos llamada `gestiona_bd`
2. Importar el fichero `scripts/gestiona_bd_estructura.sql` en la base de datos creada.
3. Arrancar la aplicación una vez para que Hibernate sincronice las tablas.
4. Ejecutar `scripts/gestiona_bd_inserts.sql` en la pestaña SQL de la base de datos **gestiona_bd**.  
Esto introducirá una serie de usuarios base en la base de datos del programa.

## Configuración de la aplicación
El fichero `src/main/resources/application.properties` ya está configurado para conectar a `gestiona_bd` con usuario `root` y contraseña vacía (configuración por defecto de XAMPP). Si tu instalación es distinta, modifica estas líneas:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestiona_bd?useSSL=false
spring.datasource.username=root
spring.datasource.password=
```

## Cómo ejecutar

En Eclipse: clic derecho sobre el proyecto → **Run As → Maven Build** → Goals: `javafx:run`

La aplicación arranca en el puerto 8080.


## Usuarios de prueba

| Usuario | Contraseña | Perfil |
|---|---|---|
| **admin** | **admin** | Administrador |
| **profesor** | **pass** | Profesor (DAM 2º) |
| nathan | pass | Profesor (DAW Diurno 2º) |
| lara | pass | Profesor (DAW Vespertino 2º) |
| shepard | pass | Profesor (DAW Virtual 2º) |
| **tutor** | **pass** | Tutor Empresa (Aperture Science) |
| eli | pass | Tutor Empresa (Black Mesa) |
| house | pass | Tutor Empresa (RobCo Industries) |
| **estudiante** | **pass** | Estudiante (con FE asignada) |
| cloud | pass | Estudiante (con FE asignada) |
| aloy | pass | Estudiante (sin FE) |
| arthur | pass | Estudiante (sin FE) |
| geralt | pass | Estudiante (con FE extraordinaria) |

---
 Asegúrate de que ese puerto no esté en uso.
