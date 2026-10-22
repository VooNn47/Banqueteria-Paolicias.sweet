
package com.grupo9.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saldo")
data class SaldoEntity(
    @PrimaryKey
    val id: Int = 1,
    val monto: Int = 0
)
