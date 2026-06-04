package com.alba.gestiona.red

import com.alba.gestiona.modelo.RespuestaEstudiante
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/login")
    suspend fun login(@Body credenciales: Map<String, String>): Response<RespuestaEstudiante>

}