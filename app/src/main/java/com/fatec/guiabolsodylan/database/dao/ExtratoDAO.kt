package com.fatec.guiabolsodylan.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fatec.guiabolsodylan.model.listaExtratoApi.Extrato

@Dao
interface ExtratoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg transacao: Extrato)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(noticias: List<Extrato>)

    @Query("SELECT * FROM extrato WHERE contaId = :contaId")
    fun all(contaId: Long): LiveData<List<Extrato>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun atualiza(vararg transacao: Extrato)
}