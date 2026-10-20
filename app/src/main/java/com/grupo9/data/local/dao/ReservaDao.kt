
package com.grupo9.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grupo9.data.local.entity.ReservaEntity

@Dao
interface ReservaDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun guardarReserva(reserva: ReservaEntity)

    @Query("SELECT * FROM reservas ORDER BY id DESC LIMIT 1")
    suspend fun obtenerReservas(): ReservaEntity?
}
