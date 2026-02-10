package com.alba.proyecto.modelo;

import java.time.LocalDate;
import java.util.Set;

public class FCT {
	
	private Long id;
	
	private LocalDate fechaInicio;
	
	private LocalDate fechaFin;

	private Estudiante estudiante;
							//una FCT solo tiene un estudiante y un tutor
	private TutorEmpresa tutor;
	
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
	
	
	
	
}
	
	
	