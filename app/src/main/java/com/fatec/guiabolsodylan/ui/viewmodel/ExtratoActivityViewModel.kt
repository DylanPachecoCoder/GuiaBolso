package com.fatec.guiabolsodylan.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fatec.guiabolsodylan.model.listaExtratoApi.Extrato
import com.fatec.guiabolsodylan.repository.ExtratoRepository
import com.fatec.guiabolsodylan.repository.Resource
import com.fatec.guiabolsodylan.usecase.DetalhesContaUseCase

class ExtratoActivityViewModel(
    repository: ExtratoRepository
) : ViewModel() {

    private val extratoUseCase = DetalhesContaUseCase(repository)

    fun buscaExtrato(contaId : Long) : LiveData<Resource<List<Extrato>?>> {
        return extratoUseCase.buscaExtrato(contaId)
    }
}