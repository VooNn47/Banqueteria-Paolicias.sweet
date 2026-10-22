package com.grupo9.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.grupo9.data.local.dao.ReservaDao
import com.grupo9.data.local.dao.SaldoDao
import com.grupo9.data.local.dao.UserDao
import com.grupo9.data.local.entity.ReservaEntity
import com.grupo9.data.local.entity.UserEntity
import com.grupo9.data.local.entity.SaldoEntity

@Database(entities = [UserEntity::class, ReservaEntity::class, SaldoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase () {

    abstract fun userDao(): UserDao
    abstract fun reservaDao(): ReservaDao

    abstract fun saldoDao(): SaldoDao
    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Myapp_databse.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}