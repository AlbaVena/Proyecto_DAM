package com.alba.proyecto.repositorios;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alba.proyecto.modelo.Empresa;
import com.alba.proyecto.modelo.TutorEmpresa;

@Repository
public interface TutorEmpresaRepository extends JpaRepository<TutorEmpresa, Long> {
	
	TutorEmpresa findByEmpresa(Empresa empresa);
	
    Optional<TutorEmpresa> findByUsuario(String usuario);
	


}
