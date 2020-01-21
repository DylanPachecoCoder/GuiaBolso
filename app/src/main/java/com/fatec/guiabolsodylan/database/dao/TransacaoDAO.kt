package com.fatec.guiabolsodylan.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.fatec.guiabolsodylan.model.Transacao
import com.fatec.guiabolsodylan.model.listaExtratoApi.Data

@Dao
interface TransacaoDAO {

    @Insert
    fun add(vararg transacao: Transacao)

    @Query("SELECT * FROM transacao WHERE contaId = :contaId")
    fun all(contaId: Long): LiveData<List<Transacao>?>

    @Insert(onConflict = REPLACE)
    fun atualiza(vararg transacao: Transacao)
}