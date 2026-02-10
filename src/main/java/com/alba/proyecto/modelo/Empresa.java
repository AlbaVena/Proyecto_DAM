package com.alba.proyecto.modelo;

import java.util.Set;

public class Empresa {
	
	private Long id;
	
	private String nombre;
	
	private String direccion;
	
	private String telefono;
	
	private Set<TutorEmpresa> tutores;

	public Empresa(Long id, String nombre, String direccion, String telefono, Set<TutorEmpresa> tutores) {
		this.id = id;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.tutores = tutores;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Set<TutorEmpresa> getTutores() {
		return tutores;
	}

	public void setTutores(Set<TutorEmpresa> tutores) {
		this.tutores = tutores;
	}
	
	
	


}
