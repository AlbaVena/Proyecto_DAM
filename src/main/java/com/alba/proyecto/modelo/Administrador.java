package com.alba.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Clase Administrador.
 * 
 * Sólo puede haber una instancia en el sistema.
 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 * 
 */
@Entity
@Table(name = "administrador")
public class Administrador extends Persona {


	private static final long serialVersionUID = 1L;

	public Administrador(Long id, String usuario, String contraseña) {
		super(id, usuario, contraseña);
		this.perfil = Perfil.ADMINISTRADOR;
	}
	
	public Administrador() {
		super();
	}



	
	

}
