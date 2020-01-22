package com.fatec.guiabolsodylan.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fatec.guiabolsodylan.model.listaExtratoApi.Data

@Dao
interface DataDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg transacao: Data)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(noticias: List<Data>)

    @Query("SELECT * FROM data WHERE contaId = :contaId")
    fun all(contaId: Long): LiveData<List<Data>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun atualiza(vararg transacao: Data)
}