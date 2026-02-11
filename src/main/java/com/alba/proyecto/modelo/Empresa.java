package com.alba.proyecto.modelo;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "empresa")
public class Empresa implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_empresa")
	private Long id;
	
	@Column(name = "nombre", length = 50, nullable = false)
	private String nombre;
	
	@Column(name = "direccion", length = 100, nullable = true)
	private String direccion;
	
	@Column(name = "telefono", length = 9, nullable = true)
	private String telefono;
	
	@OneToMany(mappedBy = "empresa")
	private Set<TutorEmpresa> tutores;

	public Empresa(Long id, String nombre, String direccion, String telefono, Set<TutorEmpresa> tutores) {
		this.id = id;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		this.tutores = tutores;
	}
	
	

	public Empresa() {
		super();
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
