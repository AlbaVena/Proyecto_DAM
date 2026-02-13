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

@Entity
@Table(name = "curso")
public class Curso {

	private final String DAW_DIURNO_CODIGO = "IFC303";
	private final String DAW_VESPERTINO_CODIGO = "VIFC303";
	private final String DAW_VIRTUAL_CODIGO = "@IFC303";
	private final String DAM_CODIGO = "VIFC302";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "codigo", nullable = false)
	private String codigo;

	@Column(name = "ciclo", nullable = false)
	private int ciclo;

	@Enumerated(EnumType.STRING)
	private TipoCurso tipoCurso;
	
	@OneToOne
	@JoinColumn(name = "FK_profesor", unique = true)
	private Profesor profesor;
	
	@OneToMany(mappedBy = "curso")
	private Set <Estudiante> estudiantes;

	public Curso(int ciclo, TipoCurso tipoCurso, Profesor profesor, Set <Estudiante> estudiantes) {
		this.ciclo = ciclo;
		this.tipoCurso = tipoCurso;
		this.codigo = calcularCodigo(ciclo, tipoCurso);
		this.profesor = profesor;
		this.estudiantes = estudiantes;
	}

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
