package com.fatec.guiabolsodylan.usecase

import android.view.MenuItem
import androidx.lifecycle.LiveData
import br.com.ajchagas.guiabolsobrq.ui.recyclerview.adapter.ListAccountAdapter
import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.repository.ContaRepository
import com.fatec.guiabolsodylan.repository.Resource

class BuscaContasUseCase(
    private val repository: ContaRepository
) {
    fun buscaContas(): LiveData<Resource<List<Conta>?>> {
        return repository.buscaContas()
    }

    fun pegaContaSelecionada(item: MenuItem, adapter: ListAccountAdapter) : Conta {
        val position = item.order
        val conta = adapter.getConta(position)
        return conta
    }

}