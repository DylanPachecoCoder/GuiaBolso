package com.fatec.guiabolsodylan.ui.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.ajchagas.guiabolsobrq.ui.recyclerview.adapter.ListAccountAdapter
import com.fatec.guiabolsodylan.R
import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.repository.Repository
import com.fatec.guiabolsodylan.repository.Resource
import kotlinx.android.synthetic.main.edita_epelido.view.*
import java.math.BigDecimal

class ListaContasActivityViewModel(
    private val repository: Repository
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

    fun configuraCampoEditaApelido(conta: Conta, context: Context): Pair<View, TextView> {
        val viewCriada = LayoutInflater.from(context)
            .inflate(R.layout.edita_epelido, null, false)
        val campoEditaApelido: TextView = viewCriada.campo_edita_apelido
        campoEditaApelido.text = conta.apelido
        return Pair(viewCriada, campoEditaApelido)
    }
}