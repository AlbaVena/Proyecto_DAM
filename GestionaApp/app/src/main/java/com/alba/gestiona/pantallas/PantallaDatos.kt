package com.alba.gestiona.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alba.gestiona.modelo.RespuestaEstudiante
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

/**
 * Pantalla que muestra los datos personales y de FE del estudiante.
 */
@Composable
fun PantallaDatos(
    estudiante: RespuestaEstudiante,
    onCerrarSesion: () -> Unit,
    onAbrirCalendario: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AzulClaro)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AzulOscuro)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Hola, ${estudiante.nombre}",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = estudiante.curso,
                    color = AzulMedio,
                    fontSize = 13.sp
                )
            }
            TextButton(onClick = onCerrarSesion) {
                Text("Cerrar sesión", color = Color.White, fontSize = 12.sp)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // tarjeta datos personales
            TarjetaSeccion(titulo = "Datos personales") {
                FilaDato("Nombre completo", "${estudiante.nombre} ${estudiante.apellidos}")
                FilaDato("Usuario", estudiante.usuario)
                FilaDato("Email", estudiante.email.ifEmpty { "—" })
                FilaDato("Teléfono", estudiante.telefono.ifEmpty { "—" })
                FilaDato("Nº Seg. Social", estudiante.nss)
            }

            // tarjeta datos FE
            TarjetaSeccion(titulo = "Formación en Empresa") {
                FilaDato("Empresa", estudiante.empresa)
                FilaDato("Tutor de empresa", estudiante.tutorEmpresa.ifEmpty { "—" })
                FilaDato("Periodo", estudiante.periodo.ifEmpty { "—" })
                FilaDato("Fecha inicio", estudiante.fechaInicio.ifEmpty { "—" })
                FilaDato("Fecha fin", estudiante.fechaFin.ifEmpty { "—" })
            }

            Button(
                onClick = onAbrirCalendario,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AzulOscuro)
            ) {
                Text("Mi calendario de asistencia", color = Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * Tarjeta con título y contenido variable.
 */
@Composable
fun TarjetaSeccion(titulo: String, contenido: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = titulo,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = AzulOscuro
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = AzulMedio
            )
            contenido()
        }
    }
}

/**
 * Fila con etiqueta y valor.
 */
@Composable
fun FilaDato(etiqueta: String, valor: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = etiqueta,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = valor,
            fontSize = 13.sp,
            color = Color.DarkGray,
            modifier = Modifier.weight(1f)
        )
    }
}

