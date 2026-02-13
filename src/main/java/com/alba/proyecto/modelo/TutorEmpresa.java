package com.alba.proyecto.modelo;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "tutor_empresa")
@PrimaryKeyJoinColumn(name = "id_persona")
public class TutorEmpresa extends Persona{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "FK_empresa_id", nullable = false)
	private Empresa empresa;
	
	@OneToMany(mappedBy = "tutor")
	private Set<FCT> fct;

	public TutorEmpresa(String usuario, String contraseña, String nombre, String apellidos, String email,
			String telefono,Perfil perfil, Empresa empresa, Set<FCT> fcts) {
		super(usuario, contraseña, nombre, apellidos, email, telefono, Perfil.TUTOREMPRESA);
		this.empresa = empresa;
		this.fct = fcts;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Set<FCT> getFcts() {
		return fct;
	}

	public void setFcts(Set<FCT> fcts) {
		this.fct = fcts;
	}
	
	


}
