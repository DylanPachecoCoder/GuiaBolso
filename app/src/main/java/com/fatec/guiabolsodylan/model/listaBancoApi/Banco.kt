package com.fatec.guiabolsodylan.model.listaBancoApi

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Banco(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id") val id: Int = 0 ,
    @SerializedName("codigo") val codigo: String = "",
    @SerializedName("nome") val nome: String = "",
    @SerializedName("agencia") val agencia : Int = 0,
    @SerializedName("conta") val conta : Int = 0,
    @SerializedName("conta_dig") val conta_dig : Int = 0,
    @SerializedName("data_alteracao") val data_alteracao: String = "",
    @SerializedName("hora_alteracadao") val hora_alteracadao : String = "",
    @SerializedName("data_criacao") val data_criacao: String = "",
    @SerializedName("hora_criacao") val hora_criacao: String = ""
){
    override fun toString() : String{
        return nome
    }
}
