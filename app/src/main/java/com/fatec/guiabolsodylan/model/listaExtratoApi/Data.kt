package com.fatec.guiabolsodylan.model.listaExtratoApi

import com.google.gson.annotations.SerializedName

class Data(
    @SerializedName("mensagem") val mensagem : String,
    @SerializedName("qtd_registros") val qtd_registros : String,
    @SerializedName("data") val data : List<Extrato>
)