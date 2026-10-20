package com.grupo9.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.grupo9.model.HomeModel

class HomeViewModel : ViewModel() {

    var uiState by mutableStateOf(HomeModel())
        private set

    fun onBusquedaChanged(busqueda: String) {
        uiState = uiState.copy(busqueda = busqueda)
    }

    fun onCategoriaChanged(categoria: String) {
        uiState = uiState.copy(categoria = categoria)
    }


    fun mostrarProducto(nombre: String, categoria: String): Boolean {
        val estado = uiState
        val categoriaOk = estado.categoria == "Todos" || estado.categoria == categoria
        val busquedaOk = estado.busqueda.isBlank() || estado.busqueda == nombre
        return categoriaOk && busquedaOk
    }
}