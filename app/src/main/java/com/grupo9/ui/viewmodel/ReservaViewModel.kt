package com.grupo9.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo9.data.local.database.AppDatabase
import com.grupo9.data.local.entity.ReservaEntity
import com.grupo9.model.ReservaModel
import com.grupo9.model.Sesion
import kotlinx.coroutines.launch

class ReservaViewmodel : ViewModel() {

    var uiState by mutableStateOf(ReservaModel())
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var successMessage by mutableStateOf<String?>(null)
        private set

    fun onNombreEventoChanged(nombre: String) {
        uiState = uiState.copy(nombreEvento = nombre)
    }

    fun onFechaChanged(fecha: String) {
        uiState = uiState.copy(fecha = fecha)
    }

    fun onHorarioChanged(horario: String) {
        uiState = uiState.copy(horario = horario)
    }

    fun onInvitadosChanged(invitados: String) {
        uiState = uiState.copy(invitados = invitados)
    }

    fun onObservacionesChanged(observaciones: String) {
        uiState = uiState.copy(observaciones = observaciones)
    }

    fun guardarReserva(context: Context) {
        val state = uiState

        // Validar campos obligatorios
        if (
            state.nombreEvento.isBlank() ||
            state.fecha.isBlank() ||
            state.horario.isBlank() ||
            state.invitados.isBlank()
        ) {
            errorMessage = "Favor completar todos los campos obligatorios."
            successMessage = null
            return
        }

        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)

            val nuevaReserva = ReservaEntity(
                nombreEvento = state.nombreEvento,
                fecha = state.fecha,
                horario = state.horario,
                invitados = state.invitados,
                observaciones = state.observaciones,
                correoCliente = Sesion.correo
            )

            db.reservaDao().guardarReserva(nuevaReserva)

            errorMessage = null
            successMessage = "¡Reserva guardada exitosamente!"
        }
    }
}