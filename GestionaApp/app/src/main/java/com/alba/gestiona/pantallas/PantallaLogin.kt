package com.alba.gestiona.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alba.gestiona.R
import com.alba.gestiona.modelo.RespuestaEstudiante
import com.alba.gestiona.viewmodel.EstadoLogin
import com.alba.gestiona.viewmodel.LoginViewModel
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.runtime.LaunchedEffect

// colores de la app
val AzulOscuro = Color(0xFF18406A)
val AzulClaro = Color(0xFFE1E8EE)
val AzulMedio = Color(0xFF4C85B9)

/**
 * Pantalla de inicio de sesión para estudiantes.
 */
@Composable
fun PantallaLogin(
    onLoginExitoso: (RespuestaEstudiante) -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val estado by viewModel.estado.collectAsState()

    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    val controladorTeclado = LocalSoftwareKeyboardController.current

    // si el login fue exitoso, navegar a la siguiente pantalla
    if (estado is EstadoLogin.Exito) {
        val estudiante = (estado as EstadoLogin.Exito).estudiante
        onLoginExitoso(estudiante)
        viewModel.reiniciar()
    }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AzulClaro)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // logo
        Image(
            painter = painterResource(id = R.drawable.logo_gestiona),
            contentDescription = "Logo Gestiona",
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Gestiona",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = AzulOscuro
        )

        Text(
            text = "Acceso para estudiantes",
            fontSize = 14.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // mensaje de error
        if (estado is EstadoLogin.Error) {
            Text(
                text = (estado as EstadoLogin.Error).mensaje,
                color = Color.Red,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (estado is EstadoLogin.Cargando) {
            CircularProgressIndicator(color = AzulOscuro)
        } else {
            Button(
                onClick = {
                    controladorTeclado?.hide()
                    viewModel.login(usuario, contrasena)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AzulOscuro)
            ) {
                Text("Iniciar sesión", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

