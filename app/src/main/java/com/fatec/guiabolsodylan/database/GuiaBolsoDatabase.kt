package com.fatec.guiabolsodylan.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fatec.guiabolsodylan.database.converter.BigDecimalConverter
import com.fatec.guiabolsodylan.database.dao.BancoDAO
import com.fatec.guiabolsodylan.database.dao.ContaDAO
import com.fatec.guiabolsodylan.database.dao.ExtratoDAO
import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.model.listaBancoApi.Banco
import com.fatec.guiabolsodylan.model.listaExtratoApi.Extrato


@Database(entities = [Conta::class, Extrato::class, Banco::class], version = 6, exportSchema = false)
@TypeConverters(*[BigDecimalConverter::class])
abstract class GuiaBolsoDatabase : RoomDatabase() {

    abstract val contaDAO: ContaDAO
    abstract val dataDAO: ExtratoDAO
    abstract val bancoDAO: BancoDAO

    companion object {

        private lateinit var db: GuiaBolsoDatabase

        fun getInstance(context: Context): GuiaBolsoDatabase {

            if (::db.isInitialized) return db

            db = Room.databaseBuilder(
                context,
                GuiaBolsoDatabase::class.java,
                "guiaBolso.db"
            )
                .fallbackToDestructiveMigration()
                .build()
            return db
        }
    }
}