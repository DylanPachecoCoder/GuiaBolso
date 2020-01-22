package com.fatec.guiabolsodylan.model.listaExtratoApi

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.fatec.guiabolsodylan.model.Conta
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.math.BigDecimal

@Entity
data class Data(
    @SerializedName("data_operacao") val data_operacao: String,
    @SerializedName("lancamento") val lancamento: String,
    @SerializedName("tipo_operacao") val tipo_operacao: String,
    @SerializedName("valor") val valor: BigDecimal,
    @SerializedName("data_alteracao") val data_alteracao: String,
    @SerializedName("data_criacao") val data_criacao: String,
    @SerializedName("hora_criacao") val hora_criacao: String,
    @ForeignKey(entity = Conta::class,
        parentColumns = ["id"],
        childColumns = ["contaId"],
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )
    var contaId : Long = 0,
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id") val id: Long = 0
) : Serializable
