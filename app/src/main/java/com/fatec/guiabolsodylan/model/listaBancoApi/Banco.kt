package com.fatec.guiabolsodylan.model.listaBancoApi

import com.google.gson.annotations.SerializedName

class Banco(
    @SerializedName("mensagem") val mensagem : String,
    @SerializedName("qtd_registros") val qtd_registros : String,
    @SerializedName("data") val data : List<Data>
)