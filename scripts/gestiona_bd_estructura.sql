-- ============================================================
-- gestiona_bd — Estructura de la base de datos
-- Proyecto Intermodular DAM 2025-2026
-- Autora: Alba Vena García
-- ============================================================
-- Instrucciones:
-- 1. Ejecutar este script en phpMyAdmin para crear la BD y las tablas
-- 2. Arrancar la aplicación una vez para que Hibernate sincronice
-- 3. Ejecutar gestiona_bd_inserts.sql para cargar los datos de prueba
-- ============================================================

CREATE DATABASE IF NOT EXISTS gestiona_bd CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE gestiona_bd;

-- ============================================================
-- TABLAS
-- ============================================================

CREATE TABLE `persona` (
  `id_persona`  BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `nombre`      VARCHAR(50)  NOT NULL,
  `apellidos`   VARCHAR(50)  NOT NULL,
  `email`       VARCHAR(50)  NOT NULL,
  `telefono`    VARCHAR(9)   DEFAULT NULL,
  `usuario`     VARCHAR(25)  NOT NULL,
  `contraseña`  VARCHAR(255) NOT NULL,
  `perfil`      VARCHAR(20)  NOT NULL,
  PRIMARY KEY (`id_persona`),
  UNIQUE KEY `UK_usuario` (`usuario`),
  UNIQUE KEY `UK_email`   (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `administrador` (
  `id_persona` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id_persona`),
  CONSTRAINT `FK_admin_persona` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id_persona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `profesor` (
  `id_persona` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id_persona`),
  CONSTRAINT `FK_profesor_persona` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id_persona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `empresa` (
  `id_empresa` BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `nombre`     VARCHAR(50)  NOT NULL,
  `direccion`  VARCHAR(100) DEFAULT NULL,
  `telefono`   VARCHAR(9)   DEFAULT NULL,
  PRIMARY KEY (`id_empresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `tutor_empresa` (
  `id_persona`    BIGINT(20) NOT NULL,
  `fk_empresa_id` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id_persona`),
  CONSTRAINT `FK_tutor_persona` FOREIGN KEY (`id_persona`)    REFERENCES `persona` (`id_persona`),
  CONSTRAINT `FK_tutor_empresa` FOREIGN KEY (`fk_empresa_id`) REFERENCES `empresa` (`id_empresa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `curso` (
  `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `codigo`      VARCHAR(255) NOT NULL,
  `ciclo`       INT(11)      NOT NULL,
  `tipo_curso`  ENUM('DAM','DAW_DIURNO','DAW_VESPERTINO','DAW_VIRTUAL') DEFAULT NULL,
  `fk_profesor` BIGINT(20)   DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_profesor` (`fk_profesor`),
  CONSTRAINT `FK_curso_profesor` FOREIGN KEY (`fk_profesor`) REFERENCES `profesor` (`id_persona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `estudiante` (
  `id_persona` BIGINT(20)  NOT NULL,
  `numero_ss`  VARCHAR(12) NOT NULL,
  `fk_curso`   BIGINT(20)  NOT NULL,
  PRIMARY KEY (`id_persona`),
  UNIQUE KEY `UK_nSS` (`numero_ss`),
  CONSTRAINT `FK_estudiante_persona` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id_persona`),
  CONSTRAINT `FK_estudiante_curso`   FOREIGN KEY (`fk_curso`)   REFERENCES `curso` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `fct` (
  `id_fct`        BIGINT(20) NOT NULL AUTO_INCREMENT,
  `fecha_inicio`  DATE       DEFAULT NULL,
  `fecha_fin`     DATE       DEFAULT NULL,
  `periodo`       ENUM('ORDINARIO','EXTRAORDINARIO') NOT NULL,
  `fk_estudiante` BIGINT(20) NOT NULL,
  `fk_tutor`      BIGINT(20) NOT NULL,
  PRIMARY KEY (`id_fct`),
  UNIQUE KEY `UK_estudiante_periodo` (`fk_estudiante`, `periodo`),
  CONSTRAINT `FK_fct_estudiante` FOREIGN KEY (`fk_estudiante`) REFERENCES `estudiante` (`id_persona`),
  CONSTRAINT `FK_fct_tutor`      FOREIGN KEY (`fk_tutor`)      REFERENCES `tutor_empresa` (`id_persona`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `falta_asistencia` (
  `id`          BIGINT(20) NOT NULL AUTO_INCREMENT,
  `fecha`       DATE       DEFAULT NULL,
  `justificado` BIT(1)     DEFAULT NULL,
  `fk_fct`      BIGINT(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_falta_fct` FOREIGN KEY (`fk_fct`) REFERENCES `fct` (`id_fct`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
