package com.alba.proyecto.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Clase Administrador.
 * 
 * Tipo de usuario con acceso completo a todas las funcionalidades del sistema.
 * Implementa el patrón Singleton para garantizar que solo existe una instancia
 * en el sistema. No puede crearse desde la aplicación, se inserta directamente
 * en BD. Extiende {@link Persona}.
 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 */
@Entity
@Table(name = "administrador")
public class Administrador extends Persona {

	private static final long serialVersionUID = 1L;

	// Instancia única del singleton
	private static Administrador instancia;

	/**
	 * Constructor con parámetros para uso interno del singleton.
	 */
	private Administrador(Long id, String usuario, String contraseña) {
		super(id, usuario, contraseña);
		this.perfil = Perfil.ADMINISTRADOR;
	}

	/**
	 * Constructor vacío para JPA. Se declara protected para evitar instanciación
	 * externa directa manteniendo la compatibilidad con Hibernate.
	 */
	protected Administrador() {
		super();
	}

	/**
	 * Devuelve la única instancia de Administrador. Si no existe, la crea con los
	 * parámetros recibidos.
	 * 
	 * @param id         Identificador del administrador.
	 * @param usuario    Nombre de usuario.
	 * @param contraseña Contraseña ya hasheada.
	 * @return La instancia única de Administrador.
	 */
	public static Administrador getInstance(Long id, String usuario, String contraseña) {
		if (instancia == null) {
			instancia = new Administrador(id, usuario, contraseña);
		}
		return instancia;
	}

}