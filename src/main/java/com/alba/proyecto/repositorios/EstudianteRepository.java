package com.alba.proyecto.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alba.proyecto.modelo.Curso;
import com.alba.proyecto.modelo.Estudiante;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long>{
	
	Estudiante findBynSS(String nss);
	
    Optional<Estudiante> findByUsuario(String usuario);
    
    long countByCurso(Curso curso);
}
