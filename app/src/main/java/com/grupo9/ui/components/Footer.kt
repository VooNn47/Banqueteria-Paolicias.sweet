package com.grupo9.ui.components



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Footer(
    copyrightText: String = "© 2026 Todos los derechos reservados.",
    backgroundColor: Color = Color (0xFF1B1B1B),
    textColor: Color = Color.Gray
){
    Box(
    modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .background(backgroundColor),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = copyrightText,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal
        )
    }
}