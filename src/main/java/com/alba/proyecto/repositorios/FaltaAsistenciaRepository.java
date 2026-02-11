package com.alba.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alba.proyecto.modelo.FaltaAsistencia;

@Repository
public interface FaltaAsistenciaRepository extends JpaRepository<FaltaAsistencia, Long>{

}
