package com.alba.proyecto.services;

import org.springframework.stereotype.Component;

import com.alba.proyecto.modelo.Persona;

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
