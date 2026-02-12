package com.alba.proyecto.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import com.alba.proyecto.modelo.Persona;

@NoRepositoryBean //es ina interfaz base para heredar, no hace falta que se cree
/*
 * <T extends Persona>
 * T es cualquier clase que EXTIENDA DE PERSONA	
 */
public interface PersonaRepository <T extends Persona> extends JpaRepository<Persona, Long>{
		//TODO esta interfaz CREO QUE SOBRA
	//lleva los metodos comunes para todos
	
	/*
	 * login
	 */
	Optional<T> findByUsuario(String usuario); //usuario exacto
	Optional<T> findByEmailIgnoreCase(String email); //no importan mayus
	
	/*
	 * encontrar persona por datos comunes
	 * con CONTAINING segun vas escribiendo va encontrando "que contengan"
	 */	
	List<T> findByNombreContainingIgnoreCase(String nombre);
	List<T> findByApellidosContainingIgnoreCase(String apellidos);
	
}
