package com.alba.proyecto.modelo;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "Persona", uniqueConstraints = {
		@UniqueConstraint(name = "UK_usuario", columnNames = "usuario"),
		@UniqueConstraint(name = "UK_email", columnNames = "email")})
@Inheritance(strategy = InheritanceType.JOINED)

public class Persona implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_persona")
	protected Long id;
	
	@Column(name = "usuario", length = 25, nullable = false)
	protected String usuario;
	
	@Column(nullable = false)
	protected String contraseña;
	
	@Column(name = "nombre", length = 50, nullable = false)
	protected String nombre;
	
	@Column(name = "nombre", length = 50, nullable = false)
	protected String apellidos;
	
	@Column(name = "nombre", length = 50, nullable = false)
	protected String email;
	
	@Column(name = "telefono", length = 9, nullable = true)
	protected String telefono;

	
	
	public Persona(Long id, String usuario, String contraseña, String nombre, String apellidos, String email,
			String telefono) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.telefono = telefono;
	}

	public Persona() {
		super();
	}

	// Constructor para ADMIN
	public void setId(Long id) {
		this.id = id;
	}

	public Persona(Long id, String usuario, String contraseña) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.contraseña = contraseña;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	
	

}
