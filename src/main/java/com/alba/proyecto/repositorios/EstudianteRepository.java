package com.alba.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alba.proyecto.modelo.Estudiante;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long>{

}
