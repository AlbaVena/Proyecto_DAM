package com.alba.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alba.proyecto.modelo.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>{

}
