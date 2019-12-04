package com.fatec.guiabolsodylan.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.fatec.guiabolsodylan.model.Conta

@Dao
interface ContaDAO {

    @Query("SELECT * FROM conta")
    fun  all(): List<Conta>

    @Query("SELECT * FROM conta WHERE id = :contaId")
    fun  findById(contaId : Long): Conta

    @Insert
    fun add(vararg conta: Conta)

    @Delete
    fun remove(vararg conta : Conta)

    @Insert(onConflict = REPLACE)
    fun update(vararg conta : Conta)
}