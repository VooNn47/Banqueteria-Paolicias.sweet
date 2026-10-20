package com.grupo9.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupo9.ui.components.BarraInferior
import com.grupo9.ui.components.Header
import com.grupo9.ui.theme.BanqueteriaTheme
import com.grupo9.ui.theme.BlancoHueso
import com.grupo9.ui.theme.Burdeos
import com.grupo9.ui.theme.MarronSuave
import com.grupo9.ui.viewmodel.AgendaViewmodel

@Composable
fun AgendaScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToReserva: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {},
    agendaViewmodel: AgendaViewmodel = viewModel()
) {
    val reservas = agendaViewmodel.uiState.reserva

    Scaffold(
        topBar = { Header(title = "Mi Agenda") },
        bottomBar = {
            BarraInferior(
                onCatalogoClick = onNavigateToHome,
                onReservasClick = onNavigateToReserva,
                onPerfilClick = onNavigateToPerfil
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
        ) {
            Text(
                text = "Agenda de eventos",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Consulta tus reservas y sus fechas programadas.",
                fontSize = 14.sp,
                color = MarronSuave
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (agendaViewmodel.errorMessage != null) {
                Text(
                    text = agendaViewmodel.errorMessage!!,
                    color = Color.Red,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = if (reservas == null) Alignment.Center else Alignment.TopCenter
            ) {
                if (reservas == null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = null,
                            tint = Burdeos,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Aún no tienes reservas",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Cuando agendes un evento, aparecerá aquí.",
                            fontSize = 14.sp,
                            color = MarronSuave
                        )
                    }
                } else {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = BlancoHueso)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = reservas.nombreEvento,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Burdeos
                            )
                            InfoFila("Fecha", reservas.fecha.toString())
                            InfoFila("Horario", reservas.horario.toString())
                            InfoFila("Invitados", reservas.invitados.toString())
                            InfoFila("Observaciones", reservas.observaciones.toString())
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoFila(etiqueta: String, valor: String) {
    Row {
        Text(
            text = "$etiqueta: ",
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
        Text(text = valor, fontSize = 15.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun AgendaScreenPreview() {
    BanqueteriaTheme {
        AgendaScreen(onNavigateToHome = {})
    }
}