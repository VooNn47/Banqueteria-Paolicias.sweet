package com.grupo9.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupo9.ui.components.BarraInferior
import com.grupo9.ui.components.Header
import com.grupo9.ui.theme.MarronSuave
import com.grupo9.ui.viewmodel.ReservaViewmodel

@Composable
fun ReservaScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToAgenda: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {},
    reservaViewModel: ReservaViewmodel = viewModel()
) {
    val uiState = reservaViewModel.uiState
    val context = LocalContext.current

    Scaffold(
        topBar = { Header(title = "Reservar evento") },
        bottomBar = {
            BarraInferior(
                onCatalogoClick = onNavigateToHome,
                onAgendaClick = onNavigateToAgenda,
                onPerfilClick = onNavigateToPerfil
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Reserva tu evento",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Completa los siguientes datos para solicitar tu reserva.",
                fontSize = 14.sp,
                color = MarronSuave
            )

            Spacer(modifier = Modifier.height(4.dp))

            CampoReserva(
                valor = uiState.nombreEvento,
                etiqueta = "Nombre del evento",
                onCambio = { reservaViewModel.onNombreEventoChanged(it) }
            )
            CampoReserva(
                valor = uiState.fecha,
                etiqueta = "Fecha del evento",
                onCambio = { reservaViewModel.onFechaChanged(it) }
            )
            CampoReserva(
                valor = uiState.horario,
                etiqueta = "Horario",
                onCambio = { reservaViewModel.onHorarioChanged(it) }
            )
            CampoReserva(
                valor = uiState.invitados,
                etiqueta = "Cantidad de invitados",
                onCambio = { reservaViewModel.onInvitadosChanged(it) },
                teclado = KeyboardType.Number
            )
            CampoReserva(
                valor = uiState.observaciones,
                etiqueta = "Observaciones",
                onCambio = { reservaViewModel.onObservacionesChanged(it) }
            )

            if (reservaViewModel.errorMessage != null) {
                Text(
                    text = reservaViewModel.errorMessage!!,
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }

            if (reservaViewModel.successMessage != null) {
                Text(
                    text = reservaViewModel.successMessage!!,
                    color = Color(0xFF2E7D32),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = { reservaViewModel.guardarReserva(context) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50)
            ) {
                Text(text = "Enviar reserva")
            }
        }
    }
}


@Composable
private fun CampoReserva(
    valor: String,
    etiqueta: String,
    onCambio: (String) -> Unit,
    teclado: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onCambio,
        label = { Text(etiqueta) },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = teclado),
        modifier = Modifier.fillMaxWidth()
    )
}