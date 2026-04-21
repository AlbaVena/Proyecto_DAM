package com.alba.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrador")
public class Administrador extends Persona {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Administrador(Long id, String usuario, String contraseña) {
		super(id, usuario, contraseña);
		this.perfil = Perfil.ADMINISTRADOR;
	}
	
	public Administrador() {
		super();
	}



	
	

}
