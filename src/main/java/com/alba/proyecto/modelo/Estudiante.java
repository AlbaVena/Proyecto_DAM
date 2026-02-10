package com.alba.proyecto.modelo;

public class Estudiante extends Persona{
	
	
	private String nSS;
	
	private Curso curso; //estudiante tiene un curso, un curso tiene N estudiantes
	
	private FCT fct; //estudiamte tiene 1 FCT, fct tiene muchos estudiantes

	public Estudiante(Long id, String usuario, String contraseña, String nombre, String apellidos, String email,
			String telefono, String nSS, Curso curso, FCT fct) {
		super(id, usuario, contraseña, nombre, apellidos, email, telefono);
		this.nSS = nSS;
		this.curso = curso;
		this.fct = fct;
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
