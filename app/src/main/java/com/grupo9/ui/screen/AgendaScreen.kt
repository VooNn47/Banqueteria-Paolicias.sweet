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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current
    val invitados = reservas?.invitados?.toIntOrNull() ?: 0

    val precio: Int

    if (invitados >= 1 && invitados <= 10) {
        precio = 30000
    } else if (invitados >= 11 && invitados <= 20) {
        precio = 50000
    } else if (invitados >= 21 && invitados <= 30) {
        precio = 80000
    } else if (invitados >= 31 && invitados <= 40) {
        precio = 100000
    } else if (invitados >= 41 && invitados <= 50) {
        precio = 120000
    } else if (invitados >= 51 && invitados <= 60) {
        precio = 140000
    } else if (invitados >= 61 && invitados <= 70) {
        precio = 160000
    } else if (invitados >= 71 && invitados <= 80) {
        precio = 180000
    } else if (invitados >= 81 && invitados <= 90) {
        precio = 200000
    } else if (invitados >= 91 && invitados <= 100) {
        precio = 220000
    } else {
        precio = 0
    }
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
                            Text(text = "Fecha: ${reservas.fecha}", fontSize = 15.sp)
                            Text(text = "Horario: ${reservas.horario}", fontSize = 15.sp)
                            Text(text = "Invitados: ${reservas.invitados}", fontSize = 15.sp)
                            Text(text = "Observaciones: ${reservas.observaciones}", fontSize = 15.sp)
                            Text(text = "Precio del evento: $$precio", fontSize = 15.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (reservas != null) {
                        agendaViewmodel.pagarReserva(context, precio)
                    }
                },
                modifier = Modifier.fillMaxWidth(),

                ) {
                Text(text = "Pagar reserva")
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun AgendaScreenPreview() {
    BanqueteriaTheme {
        AgendaScreen(onNavigateToHome = {})
    }
}