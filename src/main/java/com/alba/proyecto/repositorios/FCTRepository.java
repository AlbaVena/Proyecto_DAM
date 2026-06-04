package com.alba.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.alba.proyecto.modelo.FCT;

@Repository
public interface FCTRepository extends JpaRepository<FCT, Long>{
	
	@Query("SELECT COUNT(DISTINCT f.estudiante.id) FROM FCT f")
	Long contarEstudiantesConFE();

}
