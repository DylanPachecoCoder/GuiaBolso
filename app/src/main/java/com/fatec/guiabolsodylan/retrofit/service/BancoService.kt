package com.fatec.guiabolsodylan.retrofit.service

import com.fatec.guiabolsodylan.model.listaBancoApi.Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface BancoService {

    @GET("bancos")
    fun buscaContas(): Call<Data>

    @GET("bancos/{id}/extrato/{dataFinal}/{dataAtual}")
    fun buscaExtrato(
        @Path("id") id: Int?,
        @Path("dataAtual") dataAtual: String,
        @Path("dataFinal") ultimos30Dias: String
    ) : Call<com.fatec.guiabolsodylan.model.listaExtratoApi.Data>
}