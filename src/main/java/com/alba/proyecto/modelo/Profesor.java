package com.alba.proyecto.modelo;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "profesor")
@PrimaryKeyJoinColumn(name = "id_persona")
public class Profesor extends Persona implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(mappedBy = "profesor")
	private Curso curso;

	public Profesor(Long id, String usuario, String contraseña, String nombre, String apellidos, String email,
			String telefono, Curso curso) {
		super(id, usuario, contraseña, nombre, apellidos, email, telefono);
		this.curso = curso;

	}

	public Profesor(Long id, String usuario, String contraseña, String nombre, String apellidos, String email,
			String telefono) {
		super(id, usuario, contraseña, nombre, apellidos, email, telefono);
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

}
