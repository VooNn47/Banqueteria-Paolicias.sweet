package com.grupo9.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupo9.model.Sesion
import com.grupo9.ui.components.Footer
import com.grupo9.ui.components.Header
import com.grupo9.ui.viewmodel.AdminViewModel

@Composable
fun PanelAdminScreen(
    onLogout: () -> Unit,
    viewModel: AdminViewModel = viewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.cargarReservas(context)
    }

    Scaffold(
        topBar = { Header(title = "PANEL ADMIN") },
        bottomBar = { Footer() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Cerrar sesión
            Button(
                onClick = {
                    Sesion.correo = ""
                    Sesion.esAdmin = false
                    onLogout()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Filtros
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { viewModel.onFiltroChanged("Todas") }) { Text("Todas") }
                Button(onClick = { viewModel.onFiltroChanged("Pendiente") }) { Text("Pendiente") }
                Button(onClick = { viewModel.onFiltroChanged("Pagado") }) { Text("Pagado") }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Mensaje si no hay reservas para el filtro elegido
            if (viewModel.reservasFiltradas().isEmpty()) {
                Text("No hay reservas para mostrar")
            }

            // Lista de reservas
            LazyColumn {
                items(viewModel.reservasFiltradas()) { reserva ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                reserva.nombreEvento,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Cliente: ${reserva.correoCliente}")
                            Text("${reserva.fecha} - ${reserva.horario}")
                            Text("Invitados: ${reserva.invitados}")
                            Text("Abono: ${reserva.estadoAbono}")

                            Spacer(modifier = Modifier.height(8.dp))

                            // Si está pendiente se puede marcar pagado, y si está pagado se puede revertir
                            if (reserva.estadoAbono == "Pendiente") {
                                Button(onClick = {
                                    viewModel.cambiarEstado(context, reserva.id, "Pagado")
                                }) {
                                    Text("Marcar abono pagado")
                                }
                            } else {
                                Button(onClick = {
                                    viewModel.cambiarEstado(context, reserva.id, "Pendiente")
                                }) {
                                    Text("Marcar pago pendiente")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}