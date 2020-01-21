package com.fatec.guiabolsodylan.model.listaExtratoApi

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.math.BigDecimal

data class Data(
    @SerializedName("id") val id: Int,
    @SerializedName("data_operacao") val data_operacao : String,
    @SerializedName("lancamento") val lancamento : String,
    @SerializedName("tipo_operacao") val tipo_operacao : String,
    @SerializedName("valor") val valor : BigDecimal,
    @SerializedName("data_alteracao") val data_alteracao : String,
    @SerializedName("data_criacao") val data_criacao : String,
    @SerializedName("hora_criacao") val hora_criacao : String
    ) :Serializable
