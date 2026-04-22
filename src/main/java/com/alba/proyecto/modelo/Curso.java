package com.alba.proyecto.modelo;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Clase Curso.
 * 
 * Este Curso es impartido por un {@link Profesor},
 * y a este Curso pertenece un {@link Estudiante}.
 * 
 * El Curso está formado por un "ciclo" + código que no cambia.
 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 */
@Entity
@Table(name = "curso")
public class Curso {

	/**
	 * Los codigos de cada curso no cambian
	 */
	private final String DAW_DIURNO_CODIGO = "IFC303";
	private final String DAW_VESPERTINO_CODIGO = "VIFC303";
	private final String DAW_VIRTUAL_CODIGO = "@IFC303";
	private final String DAM_CODIGO = "VIFC302";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "codigo", nullable = false)
	private String codigo;

	/**
	 * referencia a 1º o 2º
	 */
	@Column(name = "ciclo", nullable = false)
	private int ciclo;

	/**
	 * {@link TipoCurso}.
	 */
	@Enumerated(EnumType.STRING)
	private TipoCurso tipoCurso;
	
	@OneToOne
	@JoinColumn(name = "FK_profesor", unique = true)
	private Profesor profesor;
	
	@OneToMany(mappedBy = "curso")
	private Set <Estudiante> estudiantes;

	/**
	 * Constructor de curso
	 * @param ciclo Indica 1º o 2º
	 * @param tipoCurso Indica el grupo al que pertenece.
	 * @param profesor Indica el Profesor a cargo del Curso.
	 * @param estudiantes Set formado por los Estudiantes que pertenecen al Curso.
	 */
	public Curso(int ciclo, TipoCurso tipoCurso, Profesor profesor, Set <Estudiante> estudiantes) {
		this.ciclo = ciclo;
		this.tipoCurso = tipoCurso;
		this.codigo = calcularCodigo(ciclo, tipoCurso);
		this.profesor = profesor;
		this.estudiantes = estudiantes;
	}
	public Curso() {
		super();
	}

	/**
	 * Pasados los parametros ciclo y tipoCurso, forma el código completo de un Curso.
	 * @param ciclo Indica 1º o 2º
	 * @param tipoCurso Indica el grupo al que pertenece.
	 * @return String con el código completo del curso.
	 */
	private String calcularCodigo(int ciclo, TipoCurso tipoCurso) {

		String resultado = "";

		resultado += ciclo;
		switch (tipoCurso) {
		case DAW_DIURNO:
			resultado += DAW_DIURNO_CODIGO;
			break;
		case DAW_VESPERTINO:
			resultado += DAW_VESPERTINO_CODIGO;
			break;
		case DAW_VIRTUAL:
			resultado += DAW_VIRTUAL_CODIGO;
			break;
		case DAM:
			resultado += DAM_CODIGO;
			break;
		default:
		}

		return resultado;
	}

	public Long getId() {
		return id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getCiclo() {
		return ciclo;
	}

	public void setCiclo(int ciclo) {
		this.ciclo = ciclo;
	}

	public TipoCurso getTipoCurso() {
		return tipoCurso;
	}

	public void setTipoCurso(TipoCurso tipoCurso) {
		this.tipoCurso = tipoCurso;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public Set<Estudiante> getEstudiantes() {
		return estudiantes;
	}

	public void setEstudiantes(Set<Estudiante> estudiantes) {
		this.estudiantes = estudiantes;
	}
	@Override
	public String toString() {
	    return this.ciclo + "º " + this.tipoCurso + " (" + this.codigo + ")";
	}
	

	
}
