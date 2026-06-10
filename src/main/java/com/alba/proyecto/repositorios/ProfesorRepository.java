package com.alba.proyecto.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alba.proyecto.modelo.Curso;
import com.alba.proyecto.modelo.Profesor;

/**
 * Repositorio de {@link Profesor}.
 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 */
@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
	
	/**
	 * Busca un profesor por su curso asignado.
	 * @param curso
	 * @return
	 */
	Profesor findByCurso(Curso curso);
	
	/**
	 * busca un profesor por nombre de usuario
	 * @param usuario
	 * @return
	 */
    Optional<Profesor> findByUsuario(String usuario);


}
