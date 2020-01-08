package com.fatec.guiabolsodylan.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.math.BigDecimal

@Entity
class Conta(
    var apelido: String,
    val nomeTitular: String,
    val agencia: String,
    val numeroConta: String,
    val saldo: BigDecimal = BigDecimal.ZERO,
    val idBanco : Int,
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
) : Serializable
