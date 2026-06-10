package com.alba.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alba.proyecto.modelo.FCT;

/**
 * Repositorio de {@link FCT}.
 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 */
@Repository
public interface FCTRepository extends JpaRepository<FCT, Long> {

	/**
	 * Devuelve el numero total de estudiantes distintos que tienen, al menos, una
	 * FE asignada.
	 * 
	 * @return
	 */
	@Query("SELECT COUNT(DISTINCT f.estudiante.id) FROM FCT f")
	Long contarEstudiantesConFE();

}
