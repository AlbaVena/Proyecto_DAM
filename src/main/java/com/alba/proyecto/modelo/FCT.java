package com.alba.proyecto.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Clase FCT.
 * 
 * Relación entre {@link Estudiante} y {@link Empresa} mediante un 
 * {@link TutorEmpresa}.
 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 */
@Entity
@Table(name = "fct", uniqueConstraints = {
	    @UniqueConstraint(name = "UK_estudiante_periodo", 
                columnNames = {"fk_estudiante", "periodo"})
})
public class FCT implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_fct")
	private Long id;

	@Column(name = "fechaInicio")
	private LocalDate fechaInicio;

	@Column(name = "fechaFin")
	private LocalDate fechaFin;

	@ManyToOne
	@JoinColumn(name = "FK_estudiante", nullable = false)
	private Estudiante estudiante;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "periodo", nullable = false)
	private Periodo periodo;

	@ManyToOne
	@JoinColumn(name = "FK_tutor", nullable = false)
	private TutorEmpresa tutor;

	@OneToMany(mappedBy = "fct")
	private Set<FaltaAsistencia> faltas;
	
	public FCT(Long id, LocalDate fechaInicio, LocalDate fechaFin, Estudiante estudiante, TutorEmpresa tutor,
			Set<FaltaAsistencia> faltas) {
		super();
		this.id = id;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.estudiante = estudiante;
		this.tutor = tutor;
		this.faltas = faltas;
	}
	
	

	public FCT(Long id, LocalDate fechaInicio, LocalDate fechaFin, Estudiante estudiante, Periodo periodo,
			TutorEmpresa tutor) {
		super();
		this.id = id;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.estudiante = estudiante;
		this.periodo = periodo;
		this.tutor = tutor;
	}



	public FCT() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public TutorEmpresa getTutor() {
		return tutor;
	}

	public void setTutor(TutorEmpresa tutor) {
		this.tutor = tutor;
	}

	public Set<FaltaAsistencia> getFaltas() {
		return faltas;
	}

	public void setFaltas(Set<FaltaAsistencia> faltas) {
		this.faltas = faltas;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}
	

}
