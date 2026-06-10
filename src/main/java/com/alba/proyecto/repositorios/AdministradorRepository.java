package com.alba.proyecto.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alba.proyecto.modelo.Administrador;

/**
 * Repositorio de {@link Administrador}.
 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 */
@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long>{
    
	/**
	 * Busca el administrador por su nombre de usuario
	 * @param usuario
	 * @return Administrados
	 */
	Optional<Administrador> findByUsuario(String usuario);
	

}
