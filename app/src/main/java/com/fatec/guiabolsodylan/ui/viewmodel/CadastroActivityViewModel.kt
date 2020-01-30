package com.fatec.guiabolsodylan.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.model.listaBancoApi.Banco
import com.fatec.guiabolsodylan.repository.BancoRepository
import com.fatec.guiabolsodylan.repository.ContaRepository
import com.fatec.guiabolsodylan.repository.Resource
import com.fatec.guiabolsodylan.usecase.CadastraContaUseCase
import com.google.android.material.textfield.TextInputLayout

class CadastroActivityViewModel(
    bancoRepository: BancoRepository,
    contaRepository: ContaRepository
) : ViewModel() {

    private val cadastraUseCase: CadastraContaUseCase =
        CadastraContaUseCase(contaRepository, bancoRepository)

    fun buscaBancos(): LiveData<Resource<List<Banco>?>> {
        return cadastraUseCase.buscaBancos()
    }

    fun salvaConta(novaConta: Conta) {
        cadastraUseCase.salvaConta(novaConta)
    }

    fun validaTodosOsCampos(): Boolean {
        return cadastraUseCase.validaTodosOsCampos()
    }

    fun validaCampoObrigatorio(textInputLayout: TextInputLayout) {
        cadastraUseCase.validaCampoObrigatorio(textInputLayout)
    }
}