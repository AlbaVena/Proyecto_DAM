package com.alba.proyecto.modelo;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "estudiante", uniqueConstraints = @UniqueConstraint(name = "UK_nSS", columnNames = "numero_ss"))

@PrimaryKeyJoinColumn(name = "id_persona")
public class Estudiante extends Persona implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "numero_ss", length = 12, nullable = false)
	private String nSS;

	@ManyToOne
	@JoinColumn(name = "FK_curso", nullable = false)
	private Curso curso; // estudiante tiene un curso, un curso tiene N estudiantes

	@OneToOne
	@JoinColumn(name = "FK_fct", nullable = false) // TODO corregir
	private FCT fct; // estudiamte tiene 1 FCT, 1 FCT solo pertenece a 1 estudiante

	public Estudiante(String usuario, String contraseña, String nombre, String apellidos, String email, String telefono,
			Perfil perfil, String nSS, Curso curso, FCT fct) {
		super(usuario, contraseña, nombre, apellidos, email, telefono, perfil);
		this.nSS = nSS;
		this.curso = curso;
		this.fct = fct;
	}

	public Estudiante(String usuario, String contraseña, String nombre, String apellidos, String email, String telefono,
			Perfil perfil) {
		super(usuario, contraseña, nombre, apellidos, email, telefono, Perfil.ESTUDIANTE);
	}

	public Estudiante(String usuario, String contraseña, String nombre, String apellidos, String email, String telefono,
			Perfil perfil, String nSS, Curso curso) {
		super(usuario, contraseña, nombre, apellidos, email, telefono, perfil);
		this.nSS = nSS;
		this.curso = curso;
	}

	public String getnSS() {
		return nSS;
	}

	public void setnSS(String nSS) {
		this.nSS = nSS;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public FCT getFct() {
		return fct;
	}

	public void setFct(FCT fct) {
		this.fct = fct;
	}

}
