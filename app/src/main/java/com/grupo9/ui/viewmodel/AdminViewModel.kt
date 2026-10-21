package com.grupo9.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo9.data.local.database.AppDatabase
import com.grupo9.data.local.entity.ReservaEntity
import com.grupo9.model.PanelAdminModel
import kotlinx.coroutines.launch

class AdminViewModel : ViewModel() {

    var uiState by mutableStateOf(PanelAdminModel())
        private set

    var reservas by mutableStateOf<List<ReservaEntity>>(emptyList())
        private set

    fun onFiltroChanged(filtro: String) {
        uiState = uiState.copy(filtro = filtro)
    }

    fun reservasFiltradas(): List<ReservaEntity> {
        return if (uiState.filtro == "Todas") reservas
        else reservas.filter { it.estadoAbono == uiState.filtro }
    }

    fun cargarReservas(context: Context) {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            reservas = db.reservaDao().obtenerTodas()
        }
    }

    fun cambiarEstado(context: Context, id: Int, nuevoEstado: String) {
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            db.reservaDao().actualizarEstado(id, nuevoEstado)
            reservas = db.reservaDao().obtenerTodas()
        }
    }
}