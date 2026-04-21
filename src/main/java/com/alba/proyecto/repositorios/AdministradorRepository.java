package com.alba.proyecto.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alba.proyecto.modelo.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long>{
    
	Optional<Administrador> findByUsuario(String usuario);

}
