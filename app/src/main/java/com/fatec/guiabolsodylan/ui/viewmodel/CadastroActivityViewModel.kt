package com.fatec.guiabolsodylan.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.model.listaBancoApi.Data
import com.fatec.guiabolsodylan.repository.CadastroRepository
import com.fatec.guiabolsodylan.repository.Resource
import com.fatec.guiabolsodylan.validator.ValidacaoPadrao
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivityViewModel(
    private val repository: CadastroRepository
) : ViewModel() {

    private val validators: MutableList<ValidacaoPadrao> = mutableListOf()

    fun buscaBancos() : LiveData<Resource<List<Data>?>> {
        return repository.buscaBancos()
    }

    fun salvaConta(novaConta: Conta) {
        repository.salvaConta(novaConta)
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