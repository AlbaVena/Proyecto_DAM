package com.alba.proyecto.modelo;

import java.util.Set;

public class TutorEmpresa extends Persona{
	
	private Empresa empresa;
	
	private Set<FCT> fcts;

	public TutorEmpresa(Long id, String usuario, String contraseña, String nombre, String apellidos, String email,
			String telefono, Empresa empresa, Set<FCT> fcts) {
		super(id, usuario, contraseña, nombre, apellidos, email, telefono);
		this.empresa = empresa;
		this.fcts = fcts;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Set<FCT> getFcts() {
		return fcts;
	}

	public void setFcts(Set<FCT> fcts) {
		this.fcts = fcts;
	}
	
	


}
