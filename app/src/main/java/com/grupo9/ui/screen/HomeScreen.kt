package com.grupo9.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo9.R
import com.grupo9.ui.components.BarraInferior
import com.grupo9.ui.theme.BanqueteriaTheme
import com.grupo9.ui.theme.BlancoHueso
import com.grupo9.ui.theme.Burdeos
import com.grupo9.ui.theme.Dorado
import com.grupo9.ui.theme.MarronSuave
import com.grupo9.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToReserva: () -> Unit,
    onNavigateToAgenda: () -> Unit,
    onNavigateToPerfil: () -> Unit,
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BlancoHueso)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.size(35.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Paolicias.Sweet",
                        fontFamily = FontFamily.Cursive,
                        fontSize = 39.sp
                    )
                    Text(
                        text = "Pastelería & Banquetería",
                        color = Dorado,
                        fontSize = 18.sp
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.carro),
                    contentDescription = "logo de carro de compras",
                    modifier = Modifier.size(60.dp)
                )
            }
        },
        bottomBar = {
            BarraInferior(
                onReservasClick = onNavigateToReserva,
                onAgendaClick = onNavigateToAgenda,
                onPerfilClick = onNavigateToPerfil
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            OutlinedTextField(
                value = viewModel.uiState.busqueda,
                onValueChange = { viewModel.onBusquedaChanged(it) },
                label = { Text("Buscar productos...") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BotonCategoria("Todos", Modifier.weight(1f)) { viewModel.onCategoriaChanged("Todos") }
                BotonCategoria("Tortas", Modifier.weight(1f)) { viewModel.onCategoriaChanged("Tortas") }
                BotonCategoria("Pasteles", Modifier.weight(1f)) { viewModel.onCategoriaChanged("Pasteles") }
                BotonCategoria("Postres", Modifier.weight(1f)) { viewModel.onCategoriaChanged("Postres") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                items(1) {
                    val verTorta = viewModel.mostrarProducto("Torta Frutos Rojos", "Tortas")
                    val verCheesecake = viewModel.mostrarProducto("Cheesecake Arandanos", "Postres")

                    if (verTorta || verCheesecake) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            if (verTorta) {
                                ProductoCard(
                                    nombre = "Torta Frutos Rojos",
                                    descripcion = "Torta bizcocho vainilla con crema y frutos rojos.",
                                    precio = "$45.000",
                                    imagen = R.drawable.tortafrutosrojos,
                                    modifier = Modifier.weight(1f)
                                )
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                            if (verCheesecake) {
                                ProductoCard(
                                    nombre = "Cheesecake Arandanos",
                                    descripcion = "Cheesecake cremoso con coulds de arandanos.",
                                    precio = "$38.000",
                                    imagen = R.drawable.cheskake,
                                    modifier = Modifier.weight(1f)
                                )
                            } else {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}

// Botón redondeado de categoría (Todos, Tortas, Pasteles, Postres)
@Composable
private fun BotonCategoria(
    texto: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(36.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Burdeos,
            contentColor = Color.White
        ),
        border = BorderStroke(1.dp, Dorado),
        shape = CircleShape,
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        Text(
            text = texto,
            fontSize = 13.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}


@Composable
private fun ProductoCard(
    nombre: String,
    descripcion: String,
    precio: String,
    imagen: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = BlancoHueso),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = imagen),
                contentDescription = nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = nombre, fontWeight = FontWeight.Bold)
                Text(text = descripcion, fontSize = 12.sp, color = MarronSuave)
                Text(
                    text = precio,
                    color = Burdeos,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Suppress("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    BanqueteriaTheme {
        HomeScreen(
            viewModel = HomeViewModel(),
            onNavigateToReserva = {},
            onNavigateToAgenda = {},
            onNavigateToPerfil = {}
        )
    }
}