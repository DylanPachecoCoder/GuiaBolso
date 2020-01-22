package com.fatec.guiabolsodylan.ui.viewmodel

import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.ajchagas.guiabolsobrq.ui.recyclerview.adapter.ListAccountAdapter
import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.repository.ContaRepository
import com.fatec.guiabolsodylan.repository.Resource
import java.math.BigDecimal

class ListaContasActivityViewModel(
    private val repository: ContaRepository
) : ViewModel() {


    fun buscaContas(): LiveData<Resource<List<Conta>?>> {
        return repository.buscaContas()
    }

    fun somaSaldo(contas: List<Conta>): BigDecimal {
        var saldo = BigDecimal.ZERO
        for (conta: Conta in contas) {
            saldo += conta.saldo
        }
        return saldo
    }

    fun editaApelido(conta: Conta) {
        repository.edita(conta)
    }

    fun removeConta(conta: Conta) {
        repository.removeConta(conta)
    }

    fun pegaContaSelecionada(item: MenuItem, adapter : ListAccountAdapter): Conta {
        val position = item.order
        val conta = adapter.getConta(position)
        return conta
    }
}