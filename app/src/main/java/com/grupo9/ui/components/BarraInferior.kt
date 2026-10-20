package com.grupo9.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grupo9.ui.theme.BlancoHueso
import com.grupo9.ui.theme.Burdeos
import com.grupo9.ui.theme.MarronSuave

@Composable
fun BarraInferior(
    onCatalogoClick: () -> Unit = {},
    onAgendaClick: () -> Unit = {},
    onReservasClick: () -> Unit = {},
    onPerfilClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(BlancoHueso)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ItemBarra("Catálogo", Icons.Filled.Home, onCatalogoClick)
        ItemBarra("Agenda", Icons.Filled.DateRange, onAgendaClick)
        ItemBarra("Reservas", Icons.Filled.CheckCircle, onReservasClick)
        ItemBarra("Perfil", Icons.Filled.Person, onPerfilClick)
    }
}

@Composable
private fun ItemBarra(
    texto: String,
    icono: ImageVector,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icono,
            contentDescription = texto,
            tint = Burdeos
        )
        Text(
            text = texto,
            fontSize = 12.sp,
            color = MarronSuave
        )
    }
}