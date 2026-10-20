package com.grupo9.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grupo9.data.local.database.AppDatabase
import com.grupo9.data.local.entity.UserEntity
import com.grupo9.model.RegisterModel
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel(){
    var uiState by mutableStateOf(RegisterModel())
        private set

    fun onNameChanged(name: String){
        uiState = uiState.copy(name = name, errorMessage = null)
    }

    fun onEmailChanged(email: String){
        uiState = uiState.copy(email = email, errorMessage = null)
    }

    fun onPasswordChanged(password: String){
        uiState = uiState.copy(password = password, errorMessage = null)
    }

    fun onConfirmPasswordChanged(confirmPassword: String){
        uiState = uiState.copy(confirmPassword = confirmPassword, errorMessage = null)
    }

    fun onRegisterClick(context: Context, onSuccess: () -> Unit){
        val state = uiState

        //Validar datos vacios
        if(state.name.isBlank() || state.email.isBlank() || state.password.isBlank()){
            uiState = uiState.copy(errorMessage = "Favor completar todos los campos.")
            return
        }

        //validamos coincidencias en contraseñas
        if(state.password != state.confirmPassword){
            uiState = uiState.copy(errorMessage = "Las contraseñas deben se iguales.")
            return
        }

        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val existingUser = db.userDao().getUserByEmail(state.email)

            if(existingUser != null) {
                uiState = uiState.copy(errorMessage = "El correo ya esta registrado")
            }else {
                val newUser = UserEntity(
                    name = state.name,
                    email = state.email,
                    password = state.password
                )
                db.userDao().registerUser(newUser)
                uiState = uiState.copy(errorMessage = null)
                onSuccess()
            }
        }
    }
}