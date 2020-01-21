package com.fatec.guiabolsodylan.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Transacao(
    val nome: String,
    val data: String,
    val valor: String,
    val tipo: TipoTransacao,
    @ForeignKey(entity = Conta::class,
        parentColumns = ["id"],
        childColumns = ["contaId"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )
    var contaId : Long = 0,

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
) : Serializable
