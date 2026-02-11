package com.alba.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alba.proyecto.modelo.TutorEmpresa;

@Repository
public interface TutorEmpresaRepository extends JpaRepository<TutorEmpresa, Long> {

}
