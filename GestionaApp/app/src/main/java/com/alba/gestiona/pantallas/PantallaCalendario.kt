package com.alba.gestiona.pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.alba.gestiona.modelo.RespuestaEstudiante
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

// estados posibles de un día
enum class EstadoDia { NINGUNO, ASISTENCIA, AUSENCIA }

/**
 * Pantalla de calendario de asistencia del estudiante.
 */
@Composable
fun PantallaCalendario(
    estudiante: RespuestaEstudiante,
    onVolver: () -> Unit
) {
    // mapa que guarda el estado de cada día
    val estadosDias = remember { mutableStateMapOf<LocalDate, EstadoDia>() }

    var mesActual by remember { mutableStateOf(YearMonth.now()) }
    var diaSeleccionado by remember { mutableStateOf<LocalDate?>(null) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AzulClaro)
    ) {
        // cabecera
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(AzulOscuro)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onVolver) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White
                )
            }
            Text(
                text = "Calendario de asistencia",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // tarjeta del calendario
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    // navegación entre meses
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(onClick = { mesActual = mesActual.minusMonths(1) }) {
                            Text("<")
                        }
                        Text(
                            text = mesActual.month.getDisplayName(TextStyle.FULL, Locale("es"))
                                    + " " + mesActual.year,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = AzulOscuro
                        )
                        OutlinedButton(onClick = { mesActual = mesActual.plusMonths(1) }) {
                            Text(">")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // cabeceras días de la semana
                    val diasSemana = listOf("L", "M", "X", "J", "V", "S", "D")
                    Row(modifier = Modifier.fillMaxWidth()) {
                        for (dia in diasSemana) {
                            Text(
                                text = dia,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                color = AzulOscuro,
                                fontSize = 13.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // calcular días del mes
                    val primerDia = mesActual.atDay(1)
                    val desplazamiento = (primerDia.dayOfWeek.value - 1)
                    val totalDias = mesActual.lengthOfMonth()
                    val celdas = (1..desplazamiento).map { null } +
                            (1..totalDias).map { it }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(7),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                    ) {
                        items(celdas) { numeroDia ->
                            if (numeroDia == null) {
                                Box(modifier = Modifier.aspectRatio(1f))
                            } else {
                                val fecha = mesActual.atDay(numeroDia)
                                val estado = estadosDias[fecha] ?: EstadoDia.NINGUNO

                                // comprobar si el día está dentro del rango de prácticas
                                val formateador = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy")
                                val fechaInicio = if (estudiante.fechaInicio.isNotEmpty())
                                    LocalDate.parse(estudiante.fechaInicio, formateador) else null
                                val fechaFin = if (estudiante.fechaFin.isNotEmpty())
                                    LocalDate.parse(estudiante.fechaFin, formateador) else null
                                val enPracticas = fechaInicio != null && fechaFin != null
                                        && !fecha.isBefore(fechaInicio) && !fecha.isAfter(fechaFin)

                                val colorFondo = when {
                                    estado == EstadoDia.ASISTENCIA -> Color(0xFF27AE60)
                                    estado == EstadoDia.AUSENCIA -> Color(0xFFE74C3C)
                                    !enPracticas -> Color(0xFFEEEEEE)
                                    else -> Color.Transparent
                                }
                                Box(
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .padding(2.dp)
                                        .clip(CircleShape)
                                        .background(colorFondo)
                                        .clickable(enabled = enPracticas) {
                                            diaSeleccionado = fecha
                                            mostrarDialogo = true
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = numeroDia.toString(),
                                        fontSize = 12.sp,
                                        color = when {
                                            estado != EstadoDia.NINGUNO -> Color.White
                                            !enPracticas -> Color.LightGray
                                            else -> Color.DarkGray
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // leyenda
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF27AE60))
                    )
                    Text(" Asistencia", fontSize = 12.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE74C3C))
                    )
                    Text(" Ausencia", fontSize = 12.sp)
                }
            }

            // resumen
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Resumen del mes",
                        fontWeight = FontWeight.Bold,
                        color = AzulOscuro,
                        fontSize = 14.sp
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = AzulMedio
                    )
                    val diasAsistencia = estadosDias.values.count { it == EstadoDia.ASISTENCIA }
                    val diasAusencia = estadosDias.values.count { it == EstadoDia.AUSENCIA }
                    FilaDato("Días de asistencia", diasAsistencia.toString())
                    FilaDato("Días de ausencia", diasAusencia.toString())
                }
            }

            // botón justificante
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(100, 104, 209))
            ) {
                Text("Adjuntar justificante", color = Color.White, fontSize = 16.sp)
            }
        }
    }

    // diálogo para marcar el día
    if (mostrarDialogo && diaSeleccionado != null) {
        Dialog(onDismissRequest = { mostrarDialogo = false }) {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Día ${diaSeleccionado!!.dayOfMonth}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = AzulOscuro
                    )
                    Text(text = "¿Qué quieres registrar?", fontSize = 13.sp)

                    Button(
                        onClick = {
                            estadosDias[diaSeleccionado!!] = EstadoDia.ASISTENCIA
                            mostrarDialogo = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF27AE60))
                    ) {
                        Text("Asistencia", color = Color.White)
                    }

                    Button(
                        onClick = {
                            estadosDias[diaSeleccionado!!] = EstadoDia.AUSENCIA
                            mostrarDialogo = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE74C3C))
                    ) {
                        Text("Ausencia", color = Color.White)
                    }

                    OutlinedButton(
                        onClick = {
                            estadosDias.remove(diaSeleccionado!!)
                            mostrarDialogo = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Limpiar día")
                    }
                }
            }
        }
    }
}