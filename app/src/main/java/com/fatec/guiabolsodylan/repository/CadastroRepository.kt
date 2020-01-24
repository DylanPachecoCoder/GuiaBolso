package com.fatec.guiabolsodylan.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import br.com.alura.technews.retrofit.webclient.BancoWebClient
import com.fatec.guiabolsodylan.database.GuiaBolsoDatabase
import com.fatec.guiabolsodylan.database.asynctask.BaseAsyncTask
import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.model.listaBancoApi.Banco

class CadastroRepository(
    database: GuiaBolsoDatabase,
    private val webClient: BancoWebClient = BancoWebClient()
) {
    private val mediador = MediatorLiveData<Resource<List<Banco>?>>()
    private val contaDAO = database.contaDAO
    private val bancoDAO = database.bancoDAO

    fun buscaBancos(): LiveData<Resource<List<Banco>?>> {

        mediador.addSource(buscaInterno()){ bancosEncontrados ->
            mediador.value = Resource(dado = bancosEncontrados)
        }

        val falhasDaWebApiLiveData = MutableLiveData<Resource<List<Banco>?>>()
        mediador.addSource(falhasDaWebApiLiveData) { resourceDeFalha ->
            val resourceAtual = mediador.value
            val resourceNovo: Resource<List<Banco>?> = if (resourceAtual != null) {
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

    private fun buscaInterno(): LiveData<List<Banco>?> {
        return bancoDAO.all()
    }

    private fun buscaNaApi(
        quandoFalha: (erro: String?) -> Unit
    ) {
        webClient.buscaBancos(
            quandoSucesso = {
                it?.data?.let {
                    salvaInterno(it)
                }
            }, quandoFalha = quandoFalha)
    }

    private fun salvaInterno(bancos: List<Banco>) {
        BaseAsyncTask(quandoExecuta = {
            bancoDAO.add(bancos)
        }, quandoFinaliza = {}
        ).execute()
    }

    fun salvaConta(novaConta: Conta) {
        BaseAsyncTask(quandoExecuta = {
            contaDAO.add(novaConta)
        }, quandoFinaliza = {}
        ).execute()
    }

}
