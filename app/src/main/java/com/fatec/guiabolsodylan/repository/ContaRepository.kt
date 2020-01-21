package com.fatec.guiabolsodylan.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import br.com.alura.technews.retrofit.webclient.BancoWebClient
import com.fatec.guiabolsodylan.database.GuiaBolsoDatabase
import com.fatec.guiabolsodylan.database.asynctask.BaseAsyncTask
import com.fatec.guiabolsodylan.database.dao.ContaDAO
import com.fatec.guiabolsodylan.model.Conta

class ContaRepository(
    private val dao: ContaDAO
) {

    private val mediador = MediatorLiveData<Resource<List<Conta>?>>()

    fun buscaContas(): LiveData<Resource<List<Conta>?>> {
        mediador.addSource(buscaContasInterno()) { contasEncontradas ->
            mediador.value = Resource(dado = contasEncontradas)
        }
        return mediador
    }

    private fun buscaContasInterno(): LiveData<List<Conta>> {
        return dao.all()
    }

    fun edita(conta: Conta) {
        BaseAsyncTask(
            quandoExecuta = {
                dao.update(conta)
            }, quandoFinaliza = {}
        ).execute()
    }

    fun removeConta(conta: Conta) {
        BaseAsyncTask(quandoExecuta = {
            dao.remove(conta)
        }, quandoFinaliza = {}
        ).execute()
    }
}