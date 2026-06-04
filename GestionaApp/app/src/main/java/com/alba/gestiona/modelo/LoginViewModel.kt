package com.alba.gestiona.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alba.gestiona.modelo.RespuestaEstudiante
import com.alba.gestiona.red.ClienteRetrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel de la pantalla de login.
 * Gestiona el estado del formulario y la llamada a la API.
 */
class LoginViewModel : ViewModel() {

    // estado de la pantalla
    private val _estado = MutableStateFlow<EstadoLogin>(EstadoLogin.Inactivo)
    val estado: StateFlow<EstadoLogin> = _estado

    fun login(usuario: String, contrasena: String) {

        // validación básica
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            _estado.value = EstadoLogin.Error("Usuario y contraseña son obligatorios")
            return
        }

        _estado.value = EstadoLogin.Cargando

        viewModelScope.launch {
            try {
                val credenciales = mapOf("usuario" to usuario, "contrasena" to contrasena)
                val respuesta = ClienteRetrofit.apiService.login(credenciales)

                if (respuesta.isSuccessful && respuesta.body() != null) {
                    _estado.value = EstadoLogin.Exito(respuesta.body()!!)
                } else {
                    _estado.value = EstadoLogin.Error("Usuario o contraseña incorrectos")
                }
            } catch (e: Exception) {
                _estado.value = EstadoLogin.Error("Error de conexión: ${e.message}")
            }
        }
    }

    fun reiniciar() {
        _estado.value = EstadoLogin.Inactivo
    }
}

/**
 * Estados posibles de la pantalla de login.
 */
sealed class EstadoLogin {
    object Inactivo : EstadoLogin()
    object Cargando : EstadoLogin()
    data class Exito(val estudiante: RespuestaEstudiante) : EstadoLogin()
    data class Error(val mensaje: String) : EstadoLogin()
}

