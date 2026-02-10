package com.alba.proyecto.modelo;

import java.util.Set;

public class Profesor extends Persona{
	
	private Curso curso;
	private Set<FCT>fctsACargo; //un profesor tiene varias FCTs a cargo, cada FCT solo un profesor

	public Profesor(Long id, String usuario, String contraseña, String nombre, String apellidos, String email,
			String telefono, Curso curso, Set<FCT> fctsACargo) {
		super(id, usuario, contraseña, nombre, apellidos, email, telefono);
		this.curso = curso;
		this.fctsACargo = fctsACargo;
	}
	
	
	public Curso getCurso() {
		return curso;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	public Set<FCT> getFctsACargo() {
		return fctsACargo;
	}
	public void setFctsACargo(Set<FCT> fctsACargo) {
		this.fctsACargo = fctsACargo;
	}
	
	
	
	
}
