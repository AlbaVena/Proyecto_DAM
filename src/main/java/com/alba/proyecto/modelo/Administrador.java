package com.alba.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrador")
public class Administrador extends Persona {

	public Administrador(Long id, String usuario, String contraseña) {
		super(id, usuario, contraseña);
		this.perfil = Perfil.ADMINISTRADOR;
	}



	
	

}
