package com.grupo9.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.grupo9.data.local.entity.SaldoEntity

@Dao
interface SaldoDao {

    @Query("SELECT * FROM saldo WHERE id = 1")
    suspend fun obtenerSaldo(): SaldoEntity?

    @Query(" INSERT OR IGNORE INTO saldo (id, monto) VALUES (1, 0)")
    suspend fun crearSaldoInicial()

    @Query("UPDATE saldo SET monto = monto + :monto WHERE id = 1")
    suspend fun sumarSaldo(monto: Int)

    @Query("UPDATE saldo SET monto = monto - :precio WHERE id = 1 AND monto >= :precio")
    suspend fun descontarSaldo(precio: Int): Int
}