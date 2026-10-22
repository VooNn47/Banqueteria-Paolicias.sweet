package com.grupo9.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.grupo9.R
import com.grupo9.model.Sesion
import com.grupo9.ui.components.BarraInferior
import com.grupo9.ui.components.Header
import com.grupo9.ui.theme.BanqueteriaTheme
import com.grupo9.ui.theme.BlancoHueso
import com.grupo9.ui.theme.Burdeos
import com.grupo9.ui.theme.Crema
import com.grupo9.ui.theme.Dorado
import com.grupo9.ui.theme.MarronSuave
import com.grupo9.ui.viewmodel.PerfilViewModel

@Composable
fun PerfilScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToAgenda: () -> Unit = {},
    onNavigateToReserva: () -> Unit = {},
    viewModel: PerfilViewModel = viewModel()
) {
    val context = LocalContext.current
    val perfil = viewModel.uiState
    var montoTexto by remember { mutableStateOf("") }

    viewModel.cargarPerfil(context, Sesion.correo)

    Scaffold(
        topBar = { Header("Mi Perfil") },
        bottomBar = {
            BarraInferior(
                onCatalogoClick = onNavigateToHome,
                onAgendaClick = onNavigateToAgenda,
                onReservasClick = onNavigateToReserva
            )
        },
        containerColor = Crema
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.tortafrutosrojos),
                contentDescription = "foto de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .shadow(elevation = 1.dp, shape = CircleShape)
                    .clip(CircleShape)
                    .border(4.dp, Dorado, CircleShape)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "¡Hola, ${perfil.nombre.substringBefore(" ")}!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = BlancoHueso)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Mis datos",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Burdeos
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null,
                            tint = Burdeos
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Nombre completo",
                                fontSize = 12.sp,
                                color = MarronSuave
                            )
                            Text(text = perfil.nombre, fontSize = 15.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Email,
                            contentDescription = null,
                            tint = Burdeos
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Correo electrónico",
                                fontSize = 12.sp,
                                color = MarronSuave
                            )
                            Text(text = perfil.correo, fontSize = 15.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Mi saldo",
                                fontSize = 12.sp,
                                color = MarronSuave
                            )
                            Text(text = "$${viewModel.saldo}", fontSize = 15.sp)

                            Spacer(modifier = Modifier.height(12.dp))

                            OutlinedTextField(
                                value = montoTexto,
                                onValueChange = { montoTexto = it },
                                label = {
                                    Text("Ingresar monto")
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            Button(
                                onClick = {
                                    val monto = montoTexto.toIntOrNull()

                                    if (monto != null && monto > 0) {
                                        viewModel.ingresarMonto(context, monto)
                                        montoTexto = ""
                                        Toast.makeText(context, "Monto agregado",
                                            Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Ingresa un monto válido",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Burdeos
                                )
                            ) {
                                Text("Agregar saldo")
                            }
                        }
                    }
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PerfilScreenPreview() {
    BanqueteriaTheme {
        PerfilScreen()
    }
}