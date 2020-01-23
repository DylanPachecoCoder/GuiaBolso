package com.fatec.guiabolsodylan.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import br.com.alura.technews.retrofit.webclient.BancoWebClient
import com.fatec.guiabolsodylan.database.GuiaBolsoDatabase
import com.fatec.guiabolsodylan.database.asynctask.BaseAsyncTask
import com.fatec.guiabolsodylan.database.dao.BancoDAO
import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.model.listaBancoApi.Data

class CadastroRepository(
    private val database: GuiaBolsoDatabase,
    private val webClient: BancoWebClient = BancoWebClient()
) {
    private val mediador = MediatorLiveData<Resource<List<Data>?>>()
    private val contaDAO = database.contaDAO

    fun buscaBancos(): LiveData<Resource<List<Data>?>> {

//        mediador.addSource(buscaInterno()){ bancosEncontrados ->
//            mediador.value = Resource(dado = bancosEncontrados)
//        }

        val falhasDaWebApiLiveData = MutableLiveData<Resource<List<Data>?>>()
        mediador.addSource(falhasDaWebApiLiveData) { resourceDeFalha ->
            val resourceAtual = mediador.value
            val resourceNovo: Resource<List<Data>?> = if (resourceAtual != null) {
                Resource(dado = resourceAtual.dado, erro = resourceDeFalha.erro)
            } else {
                resourceDeFalha
            }
            mediador.value = resourceNovo
        }

        buscaNaApi(
            quandoFalha = { erro ->
                falhasDaWebApiLiveData.value = Resource(dado = null, erro = erro)
            })
        return mediador
    }

    private fun buscaNaApi(
        quandoFalha: (erro: String?) -> Unit
    ) {
        webClient.buscaBancos(
            quandoSucesso = {
                it?.data?.let {
                    mediador.value = Resource(dado = it)
                }
            }, quandoFalha = quandoFalha)
    }

    fun salvaConta(novaConta: Conta) {
        BaseAsyncTask(quandoExecuta = {
            contaDAO.add(novaConta)
        }, quandoFinaliza = {}
        ).execute()
    }

}
