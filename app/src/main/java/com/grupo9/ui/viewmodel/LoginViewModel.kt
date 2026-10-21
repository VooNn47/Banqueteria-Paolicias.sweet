package com.grupo9.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo9.data.local.database.AppDatabase
import com.grupo9.model.LoginModel
import com.grupo9.model.Sesion
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var uiState by mutableStateOf(LoginModel())
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun onEmailChanged(email: String) {
        uiState = uiState.copy(email = email)
        errorMessage = null
    }

    fun onPasswordChanged(password: String) {
        uiState = uiState.copy(password = password)
        errorMessage = null
    }

    fun onLoginClick(context: Context, onSuccess: () -> Unit) {
        val email = uiState.email
        val password = uiState.password

        if (email.isBlank() || password.isBlank()) {
            errorMessage = "Ingresa correo y contraseña"
            return
        }

        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val user = db.userDao().getUserByEmail(email)

            if (user != null && user.password == password) {
                errorMessage = null
                Sesion.correo = email
                Sesion.esAdmin = (email == "admin@paolicias.cl")
                onSuccess()
            } else {
                errorMessage = "Correo o contraseña incorrectos"
            }
        }
    }
}