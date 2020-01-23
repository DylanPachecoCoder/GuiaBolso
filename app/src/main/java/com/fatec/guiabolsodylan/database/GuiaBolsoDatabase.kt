package com.fatec.guiabolsodylan.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fatec.guiabolsodylan.database.converter.BigDecimalConverter
import com.fatec.guiabolsodylan.database.converter.TipoTransacaoConverter
import com.fatec.guiabolsodylan.database.dao.BancoDAO
import com.fatec.guiabolsodylan.database.dao.ContaDAO
import com.fatec.guiabolsodylan.database.dao.DataDAO
import com.fatec.guiabolsodylan.database.dao.TransacaoDAO
import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.model.Transacao
import com.fatec.guiabolsodylan.model.listaExtratoApi.Data

@Database(entities = [Conta::class, Transacao::class, Data::class], version = 5, exportSchema = false)
@TypeConverters(*[BigDecimalConverter::class, TipoTransacaoConverter::class])
abstract class GuiaBolsoDatabase : RoomDatabase() {

    abstract val contaDAO: ContaDAO
    abstract val transacaoDAO: TransacaoDAO
    abstract val dataDAO: DataDAO

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