package com.alba.proyecto.services;

import org.springframework.stereotype.Component;

import com.alba.proyecto.modelo.Persona;

/**
 * Clase Sesion.
 * 
 * Componente de Spring que almacena el usuario autenticado
 * durante la sesión activa de la aplicación.
 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 */
@Component
public class Sesion {
	
	private Persona usuarioActual;
	
	public Persona getUsuarioActual() {
		return usuarioActual;
	}
	
	public void setUsuarioActual(Persona usuarioActual) {
		this.usuarioActual = usuarioActual;
	}
	

}
