package com.fatec.guiabolsodylan.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import br.com.alura.technews.retrofit.webclient.BancoWebClient
import com.fatec.guiabolsodylan.database.dao.TransacaoDAO
import com.fatec.guiabolsodylan.model.Transacao
import com.fatec.guiabolsodylan.model.listaExtratoApi.Data

class ExtratoRepository(
    private val dao: TransacaoDAO,
    private val webClient: BancoWebClient = BancoWebClient()
) {

    private val mediador = MediatorLiveData<Resource<List<Data>?>>()

    fun buscaExtrato(contaId: Long): LiveData<Resource<List<Data>?>> {

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

//        buscaNaApi(
//            quandoFalha = { erro ->
//                falhasDaWebApiLiveData.value = Resource(dado = null, erro = erro)
//            }, contaId = contaId.toString()
//        )

        val contaIdInt = contaId.toInt()
        webClient.buscaExtrato(
            contaIdInt,
            "20191111",
            "20191113",
            quandoSucesso = { Extrato ->
                Extrato?.data?.let { transacoes ->
                    mediador.value = Resource(dado = transacoes)
                }
            }, quandoFalha = { erro ->
                falhasDaWebApiLiveData.value = Resource(dado = null, erro = erro)
            }
        )
        return mediador
    }

    private fun buscaNaApi(
        contaId: String,
        quandoFalha: (erro: String?) -> Unit
    ) {
        val contaIdInt = Integer.valueOf(contaId)
        webClient.buscaExtrato(
            contaIdInt,
            "20191111",
            "20191113",
            quandoSucesso = { Extrato ->
                Extrato?.data?.let { transacoes ->

                }
            }, quandoFalha = quandoFalha
        )
    }

    private fun buscaExtratoInterno(contaId: Long): LiveData<List<Transacao>?> {
        return dao.all(contaId = contaId)
    }

//    private fun salvaExtratoInterno(transacoes: List<Data>) {
//        dao.addAll(transacoes)
//    }
}