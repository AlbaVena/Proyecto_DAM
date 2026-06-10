package com.alba.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alba.proyecto.modelo.FaltaAsistencia;

/**
 * Repositorio de {@link FaltaAsistencia}.
 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 */
@Repository
public interface FaltaAsistenciaRepository extends JpaRepository<FaltaAsistencia, Long>{

}
