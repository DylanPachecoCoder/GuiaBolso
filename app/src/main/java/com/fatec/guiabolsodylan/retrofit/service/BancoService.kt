package com.fatec.guiabolsodylan.retrofit.service

import com.fatec.guiabolsodylan.model.Banco
import retrofit2.Call
import retrofit2.http.GET

interface BancoService {

    @GET("bancos")
    fun buscaContas(): Call<List<Banco>>
}