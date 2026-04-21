package com.alba.proyecto.modelo;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class FaltaAsistencia {
	
	@Id
	private Long id;
	
	private LocalDate fecha;
	
	private Boolean justificado;
	
	private FCT fct; //una falta pertenece a una sola FCT

	public FaltaAsistencia(Long id, LocalDate fecha, Boolean justificado, FCT fct) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.justificado = justificado;
		this.fct = fct;
	}
	public FaltaAsistencia() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Boolean getJustificado() {
		return justificado;
	}

	public void setJustificado(Boolean justificado) {
		this.justificado = justificado;
	}

	public FCT getFct() {
		return fct;
	}

	public void setFct(FCT fct) {
		this.fct = fct;
	}
	
	

	

}
