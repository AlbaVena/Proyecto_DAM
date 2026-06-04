package com.alba.gestiona.red

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Objeto singleton que proporciona el cliente Retrofit configurado.
 * La IP 10.0.2.2 apunta a localhost del ordenador desde el emulador.
 */
object ClienteRetrofit {

    // usar 10.0.2.2 con emulador, o la IP local del ordenador con móvil físico
    private const val URL_BASE = "http://10.0.2.2:8080/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}