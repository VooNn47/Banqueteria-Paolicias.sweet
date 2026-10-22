package com.grupo9.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo9.data.local.database.AppDatabase
import com.grupo9.data.local.entity.ReservaEntity
import com.grupo9.model.AgendaModel
import kotlinx.coroutines.launch

class AgendaViewmodel : ViewModel() {

    var uiState by mutableStateOf(AgendaModel())

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun cargarReservas(reservas: AgendaModel) {
        uiState = reservas
        errorMessage = null
    }

    fun mostrarError(mensaje: String) {
        errorMessage = mensaje
    }
    fun cargarReserva(context: Context) {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val reserva = db.reservaDao().obtenerReservas()

            uiState = uiState.copy(reserva = reserva)
        }
    }
    fun pagarReserva(context: Context, precio: Int) {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)

            val saldoGuardado = db.saldoDao().obtenerSaldo()

            if (saldoGuardado != null && saldoGuardado.monto >= precio) {
                val reserva = uiState.reserva

                if (reserva != null && !reserva.pagada) {
                    val resultado = db.saldoDao().descontarSaldo(precio)

                    if (resultado > 0) {
                        db.reservaDao().marcarReservaPagada(reserva.id)
                        cargarReserva(context)
                    }
                } else {
                    errorMessage = "La reserva ya está pagada o no existe."
                }
            } else {
                errorMessage = "No tienes saldo suficiente para pagar la reserva."
            }
        }
    }
}