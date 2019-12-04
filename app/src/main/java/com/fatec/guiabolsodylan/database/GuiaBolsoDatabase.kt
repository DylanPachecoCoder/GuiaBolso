package com.fatec.guiabolsodylan.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fatec.guiabolsodylan.database.converter.BigDecimalConverter
import com.fatec.guiabolsodylan.database.converter.CalendarConverter
import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.database.dao.ContaDAO

@Database(entities = [Conta::class], version = 2, exportSchema = false)
@TypeConverters(*[BigDecimalConverter::class])
abstract class GuiaBolsoDatabase : RoomDatabase() {
    abstract fun contaDAO(): ContaDAO

    companion object{
        fun getInstance(context: Context) : GuiaBolsoDatabase {
            return Room.databaseBuilder(
                context,
                GuiaBolsoDatabase::class.java,
                "guiaBolso.db"
            )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}