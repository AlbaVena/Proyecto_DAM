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

	public Profesor(String usuario, String contraseña, String nombre, String apellidos, String email,
			String telefono,Perfil perfil, Curso curso) {
		super(usuario, contraseña, nombre, apellidos, email, telefono, Perfil.PROFESOR);
		this.curso = curso;

	}

	public Profesor(String usuario, String contraseña, String nombre, String apellidos, String email,
			String telefono, Perfil perfil) {
		super(usuario, contraseña, nombre, apellidos, email, telefono, Perfil.PROFESOR);
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

}
