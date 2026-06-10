package com.alba.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alba.proyecto.modelo.Curso;

/**
 * Repositorio de {@link Curso}.
 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 */
@Repository
public interface CursoRepository extends JpaRepository<Curso, Long>{

}
