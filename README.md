# GESTIONA

Aplicación de escritorio para la gestión de Formaciones en Empresa del Departamento de Informática del CIFP La Laboral.

Incluye una aplicación móvil Android para el perfil de Estudiante.

## Tecnologías
- Java 23 + Spring Boot 3.4.0
- JavaFX 23 + FXML + Scene Builder
- JPA + Hibernate 6.6.2
- MySQL (XAMPP)
- JasperReports  6.20.0
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

## Configuración de la aplicación de escritorio
El fichero `src/main/resources/application.properties` ya está configurado para conectar a `gestiona_bd` con usuario `root` y contraseña vacía (configuración por defecto de XAMPP). Si tu instalación es distinta, modifica estas líneas:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestiona_bd?useSSL=false
spring.datasource.username=root
spring.datasource.password=
```

## Cómo ejecutar

La aplicación se distribuye como instalador MSI generado con jpackage. El instalador incluye la máquina virtual Java, por lo que no es necesario tener Java instalado en el equipo de destino.  

### Pasos:
1. Ejecutar `Gestiona-1.0.msi` y seguir el asistente de instalaciíon.
2. Si no se elige un destino diferente, la aplicación quedará instalada en `C:\Program Files\Gestiona\`, añadiendo un acceso directo en el escritorio y en el menú de inicio.
3. Antes de arrancar la aplicación, asegurarse de que XAMPP tiene MySQL activo y la base de datos ha sido importada.
4. Abrir la aplicación desde el acceso directo.


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

## Estructura del repositorio

```
Proyecto_DAM/
├── src/                  # Código fuente Java/JavaFX
├── GestionaApp/          # Aplicación Android (Kotlin + Jetpack Compose)
├── scripts/              # Scripts SQL de la base de datos
│   ├── gestiona_bd_estructura.sql
│   └── gestiona_bd_inserts.sql
├── documentacion/        # Plan de pruebas y guía de estilos
├── doc/                  # Javadoc generado
├── images/               # Diagramas e imágenes del proyecto
├── libs/                 # Repositorio Maven local (componente PasswordFieldValidado)
└── README.md
```

## Aplicación móvil

La aplicación móvil está desarrollada en Kotlin con Jetpack Compose y se encuentra en la carpeta `GestionaApp/`. Está diseñada para usarse con el emulador de Android Studio.

### Requisitos:
- Android Studio instalado
- Clonar el repositorio de GitHub para tener acceso a GestionaApp/ desde https://github.com/AlbaVena/Proyecto_DAM/tree/version2/GestionaApp .
- Importar en Android Studio.
- La **aplicación de escritorio arrancada** y con XAMPP activo
- El puerto 8080 libre en el equipo

### Pasos:
1. Abrir Android Studio e importar el proyecto `GestionaApp/` 
2. Arrancar la aplicación de escritorio desde Eclipse: lanzará la aplicación JavaFx y el servidor
3. Ejecutar la app en el emulador de Android Studio

La app usa la dirección `10.0.2.2:8080` para conectarse a la aplicación de escritorio, que es la IP que el emulador de Android asigna automáticamente al localhost del ordenador. Si se usa un dispositivo físico en lugar del emulador, hay que cambiar esa IP por la IP local del ordenador en el fichero `ClienteRetrofit.kt`.

## Autora

Alba Vena García - 2VIFC302 - CIFP La Laboral de Gijón - 2025/2026
