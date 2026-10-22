package com.grupo9.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo9.data.local.database.AppDatabase
import com.grupo9.model.PerfilModel
import kotlinx.coroutines.launch

class PerfilViewModel : ViewModel() {

    var uiState by mutableStateOf(PerfilModel())
        private set

    var saldo by mutableStateOf(0)
        private set

    fun cargarPerfil(context: Context, email: String) {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val user = db.userDao().getUserByEmail(email)

            if (user != null) {
                uiState = PerfilModel(
                    nombre = user.name,
                    correo = user.email
                )
            }
            db.saldoDao().crearSaldoInicial()
            cargarSaldo(context)
        }
    }
    fun cargarSaldo(context: Context) {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val saldoGuardado = db.saldoDao().obtenerSaldo()

            if (saldoGuardado != null) {
                saldo = saldoGuardado.monto
            }
        }
    }

    fun ingresarMonto(context: Context, monto: Int) {
        if (monto <= 0) {
            return
        }

        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)

            db.saldoDao().crearSaldoInicial()
            db.saldoDao().sumarSaldo(monto)

            val saldoGuardado = db.saldoDao().obtenerSaldo()

            if (saldoGuardado != null) {
                saldo = saldoGuardado.monto
            }
        }
    }
}
