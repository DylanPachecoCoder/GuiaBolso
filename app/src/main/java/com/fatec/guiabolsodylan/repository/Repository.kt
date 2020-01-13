package com.fatec.guiabolsodylan.repository

import br.com.alura.technews.retrofit.webclient.BancoWebClient
import com.fatec.guiabolsodylan.database.dao.ContaDAO

class Repository(private val dao : ContaDAO,
                 private val webClient : BancoWebClient = BancoWebClient()) {

}