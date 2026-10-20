package com.grupo9.model

import com.grupo9.data.local.entity.ReservaEntity

data class AgendaModel(
    val nombreEvento: String = "",
    val fecha: String = "",
    val horario: String = "",
    val invitados: String = "",
    val observaciones: String = "",
    val reserva: ReservaEntity? = null
)