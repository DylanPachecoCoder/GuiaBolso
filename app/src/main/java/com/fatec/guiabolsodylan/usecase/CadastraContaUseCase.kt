package com.fatec.guiabolsodylan.usecase

import androidx.lifecycle.LiveData
import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.model.listaBancoApi.Banco
import com.fatec.guiabolsodylan.repository.BancoRepository
import com.fatec.guiabolsodylan.repository.ContaRepository
import com.fatec.guiabolsodylan.repository.Resource
import com.fatec.guiabolsodylan.validator.ValidacaoPadrao
import com.google.android.material.textfield.TextInputLayout

class CadastraContaUseCase(
    private val contaRepository: ContaRepository,
    private val bancoRepository: BancoRepository
) {

    private val validators: MutableList<ValidacaoPadrao> = mutableListOf()

    fun buscaBancos(): LiveData<Resource<List<Banco>?>> {
        return bancoRepository.buscaBancos()
    }

    fun salvaConta(novaConta: Conta) {
        contaRepository.salvaConta(novaConta)
    }

    fun validaTodosOsCampos(): Boolean {
        var estaValido = true
        for (validator in validators) {
            if (!validator.estaValido()) {
                estaValido = false
            }
        }
        return estaValido
    }

    fun validaCampoObrigatorio(textInputLayout: TextInputLayout) {
        val validacaoPadrao = ValidacaoPadrao(textInputLayout)
        val editText = textInputLayout.editText
        validators.add(validacaoPadrao)
        editText?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validacaoPadrao.estaValido()
            }
        }
    }
}