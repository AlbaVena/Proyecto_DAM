package com.alba.proyecto.modelo;

/**
 * Clase DatoGrafico.
 * 
 * clase auxiliar para los datos de g´raficos de Jaspersoft
 * 
 * @author ALBA VENA GARCIA
 * @version 1.0
 * @since 2026
 */
public class DatoGrafico {

	private String categoria;
	private Long valor;

	public DatoGrafico(String categoria, Long valor) {
		this.categoria = categoria;
		this.valor = valor;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Long getValor() {
		return valor;
	}

	public void setValor(Long valor) {
		this.valor = valor;
	}

}
