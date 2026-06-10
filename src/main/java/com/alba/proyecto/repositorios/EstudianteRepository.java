package com.alba.proyecto.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alba.proyecto.modelo.Administrador;
import com.alba.proyecto.modelo.Curso;
import com.alba.proyecto.modelo.Estudiante;

/**
 * Repositorio de {@link Estudiante}.
 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 */
@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long>{
	
	/**
	 * Busca un estudiante por su numero de la seguridad social
	 * @param nss
	 * @return
	 */
	Estudiante findBynSS(String nss);
	
	/**
	 * busca un estudiante por su nombre de usuario
	 * @param usuario
	 * @return
	 */
    Optional<Estudiante> findByUsuario(String usuario);
    
    /**
     * devuelve el numero de estudiantes que pertenecen al curso indicado.
     * @param curso
     * @return
     */
    long countByCurso(Curso curso);

}
