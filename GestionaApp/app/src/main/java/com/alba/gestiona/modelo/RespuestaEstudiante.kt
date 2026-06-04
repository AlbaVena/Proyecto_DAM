package com.alba.gestiona.modelo

data class RespuestaEstudiante(
    val id: Long,
    val nombre: String,
    val apellidos: String,
    val email: String,
    val telefono: String,
    val usuario: String,
    val curso: String,
    val nss: String,
    val empresa: String,
    val tutorEmpresa: String,
    val fechaInicio: String,
    val fechaFin: String,
    val periodo: String
)
