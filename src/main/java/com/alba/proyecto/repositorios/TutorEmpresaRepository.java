package com.alba.proyecto.repositorios;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alba.proyecto.modelo.Empresa;
import com.alba.proyecto.modelo.TutorEmpresa;

/**
 * Repositorio de {@link TutorEmpresa}.
 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 */
@Repository
public interface TutorEmpresaRepository extends JpaRepository<TutorEmpresa, Long> {
	
	/**
	 * Busca el tutor asignado a una empresa.
	 * @param empresa
	 * @return
	 */
	TutorEmpresa findByEmpresa(Empresa empresa);
	
	/**
	 * busca un tutor por su nombre de usuario.
	 * @param usuario
	 * @return
	 */
    Optional<TutorEmpresa> findByUsuario(String usuario);


	


}
