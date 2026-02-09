package com.alba.proyecto.modelo;

public class Curso {

	private final String DAW_DIURNO_CODIGO = "IFC303";
	private final String DAW_VESPERTINO_CODIGO = "VIFC303";
	private final String DAW_VIRTUAL_CODIGO = "@IFC303";
	private final String DAM_CODIGO = "VIFC302";

	private Long id;

	private String codigo;

	private int ciclo;

	private TipoCurso tipoCurso;

	public Curso(int ciclo, TipoCurso tipoCurso) {
		this.ciclo = ciclo;
		this.tipoCurso = tipoCurso;
		this.codigo = calcularCodigo(ciclo, tipoCurso);
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

}
