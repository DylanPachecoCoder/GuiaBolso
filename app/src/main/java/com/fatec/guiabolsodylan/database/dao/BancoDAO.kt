package com.fatec.guiabolsodylan.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fatec.guiabolsodylan.model.listaBancoApi.Banco


@Dao
interface BancoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(vararg banco: Banco)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(bancos: List<Banco>)

    @Query("SELECT * FROM banco")
    fun all(): LiveData<List<Banco>?>

}