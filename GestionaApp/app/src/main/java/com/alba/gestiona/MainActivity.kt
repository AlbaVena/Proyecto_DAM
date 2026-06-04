package com.alba.gestiona

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.alba.gestiona.modelo.RespuestaEstudiante
import com.alba.gestiona.pantallas.PantallaDatos
import com.alba.gestiona.pantallas.PantallaLogin
import com.alba.gestiona.ui.theme.GestionaAppTheme

/**
 * Actividad principal. Gestiona la navegación entre pantallas.
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestionaAppTheme {
                AppNavegacion()
            }
        }
    }
}

/**
 * Composable que define la navegación entre pantallas.
 */
@Composable
fun AppNavegacion() {
    val navController = rememberNavController()
    var estudianteActual by remember { mutableStateOf<RespuestaEstudiante?>(null) }

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            PantallaLogin(
                onLoginExitoso = { estudiante ->
                    estudianteActual = estudiante
                    navController.navigate("datos") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("datos") {
            estudianteActual?.let { estudiante ->
                PantallaDatos(
                    estudiante = estudiante,
                    onCerrarSesion = {
                        estudianteActual = null
                        navController.navigate("login") {
                            popUpTo("datos") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}