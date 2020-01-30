package com.fatec.guiabolsodylan.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.fatec.guiabolsodylan.database.dao.ContaDAO
import com.fatec.guiabolsodylan.model.Conta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        CoroutineScope(Dispatchers.IO).launch {
            dao.update(conta)
        }
    }

    fun removeConta(conta: Conta) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.remove(conta)
        }
    }

    fun salvaConta(novaConta: Conta) {
        CoroutineScope(Dispatchers.IO).launch {
            dao.add(novaConta)
        }
    }
}