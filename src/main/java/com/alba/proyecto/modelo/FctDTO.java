package com.alba.proyecto.modelo;

/**
 * clase para transportar los datos a JasperSoft
 */
public class FctDTO {

	private String nombreEstudiante;
	private String nombreEmpresa;
	private String nombreTutor;
	private String periodo;
	private String fechaInicio;
	private String fechaFin;

	public FctDTO(String nombreEstudiante, String nombreEmpresa, String nombreTutor, String periodo, String fechaInicio,
			String fechaFin) {
		this.nombreEstudiante = nombreEstudiante;
		this.nombreEmpresa = nombreEmpresa;
		this.nombreTutor = nombreTutor;
		this.periodo = periodo;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public String getNombreEstudiante() {
		return nombreEstudiante;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public String getNombreTutor() {
		return nombreTutor;
	}

	public String getPeriodo() {
		return periodo;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

}
