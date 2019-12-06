package com.fatec.guiabolsodylan.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.fatec.guiabolsodylan.model.Transacao

@Dao
interface TransacaoDAO {

    @Insert
    fun add(vararg transacao: Transacao)

    @Query("SELECT * FROM transacao WHERE contaId = :contaId")
    fun all(contaId: Long): List<Transacao>

    @Insert(onConflict = REPLACE)
    fun atualiza(vararg transacao: Transacao)
}