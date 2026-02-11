package com.alba.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alba.proyecto.modelo.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long>{

	Persona findByNombre(String nombre);
	
}
