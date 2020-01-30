package com.fatec.guiabolsodylan.ui.viewmodel

import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.ajchagas.guiabolsobrq.ui.recyclerview.adapter.ListAccountAdapter
import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.repository.ContaRepository
import com.fatec.guiabolsodylan.repository.Resource
import com.fatec.guiabolsodylan.usecase.BuscaContasUseCase
import com.fatec.guiabolsodylan.usecase.EditaApelidoUseCase
import com.fatec.guiabolsodylan.usecase.RemoveContaUseCase
import com.fatec.guiabolsodylan.usecase.SomaSaldoUseCase
import java.math.BigDecimal

class ListaContasActivityViewModel(
    repository: ContaRepository
) : ViewModel() {

    private val buscaContasUseCase: BuscaContasUseCase = BuscaContasUseCase(repository)
    private val editaApelidoUseCase: EditaApelidoUseCase = EditaApelidoUseCase(repository)
    private val removeContasUseCase : RemoveContaUseCase = RemoveContaUseCase(repository)
    private val somaSaldoUseCase : SomaSaldoUseCase = SomaSaldoUseCase()

    fun buscaContas(): LiveData<Resource<List<Conta>?>> {
        return buscaContasUseCase.buscaContas()
    }

    fun pegaContaSelecionada(item: MenuItem, adapter: ListAccountAdapter): Conta {
        return buscaContasUseCase.pegaContaSelecionada(item, adapter)
    }

    fun editaApelido(conta: Conta) {
        editaApelidoUseCase.editaApelido(conta)
    }

    fun removeConta(conta: Conta) {
        removeContasUseCase.removeConta(conta)
    }

    fun somaSaldo(contas: List<Conta>): BigDecimal {
        return somaSaldoUseCase.somaSaldo(contas)
    }
}