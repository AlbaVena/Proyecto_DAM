package com.alba.proyecto.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alba.proyecto.modelo.Curso;
import com.alba.proyecto.modelo.Profesor;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
	
	Profesor findByCurso(Curso curso);
	
    Optional<Profesor> findByUsuario(String usuario);

}
