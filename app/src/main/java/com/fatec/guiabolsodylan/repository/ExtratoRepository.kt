package com.fatec.guiabolsodylan.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import br.com.alura.technews.retrofit.webclient.BancoWebClient
import com.fatec.guiabolsodylan.database.asynctask.BaseAsyncTask
import com.fatec.guiabolsodylan.database.dao.ExtratoDAO
import com.fatec.guiabolsodylan.model.listaExtratoApi.Extrato

class ExtratoRepository(
    private val dao: ExtratoDAO,
    private val webClient: BancoWebClient = BancoWebClient()
) {

    private val mediador = MediatorLiveData<Resource<List<Extrato>?>>()

    fun buscaExtrato(contaId: Long): LiveData<Resource<List<Extrato>?>> {

        mediador.addSource(buscaInterno(contaId)){ transacoesEncontradas ->
            mediador.value = Resource(dado = transacoesEncontradas)
        }

        val falhasDaWebApiLiveData = MutableLiveData<Resource<List<Extrato>?>>()
        mediador.addSource(falhasDaWebApiLiveData) { resourceDeFalha ->
            val resourceAtual = mediador.value
            val resourceNovo: Resource<List<Extrato>?> = if (resourceAtual != null) {
                Resource(dado = resourceAtual.dado, erro = resourceDeFalha.erro)
            } else {
                resourceDeFalha
            }
            mediador.value = resourceNovo
        }
        buscaNaApi(
            contaId.toInt(),
            quandoFalha = { erro ->
                falhasDaWebApiLiveData.value = Resource(dado = null, erro = erro)
            })
        return mediador
    }

    private fun buscaNaApi(
        contaId: Int,
        quandoFalha: (erro: String?) -> Unit
    ) {
        webClient.buscaExtrato(
            contaId,
            "20191111",
            "20191113",
            quandoSucesso = { Extrato ->
                Extrato?.data?.let { transacoes ->
                    salvaInterno(transacoes, contaId.toLong())
                }
            }, quandoFalha = quandoFalha
        )
    }

    private fun buscaInterno(contaId: Long): LiveData<List<Extrato>?> {
        return dao.all(contaId)
    }

    private fun salvaInterno(transacoes: List<Extrato>, contaId: Long) {
        BaseAsyncTask(
            quandoExecuta = {
                for(transacao : Extrato in transacoes){
                    transacao.contaId = contaId
                    dao.add(transacao)
                }
            }, quandoFinaliza = {}
        ).execute()
    }
}