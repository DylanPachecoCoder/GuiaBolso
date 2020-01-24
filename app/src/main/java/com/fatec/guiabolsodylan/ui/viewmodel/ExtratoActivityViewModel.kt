package com.fatec.guiabolsodylan.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fatec.guiabolsodylan.model.listaExtratoApi.Extrato
import com.fatec.guiabolsodylan.repository.ExtratoRepository
import com.fatec.guiabolsodylan.repository.Resource

class ExtratoActivityViewModel(
    private val repository: ExtratoRepository
) : ViewModel() {

    fun buscaExtrato(contaId : Long) : LiveData<Resource<List<Extrato>?>> {
        return repository.buscaExtrato(contaId)
    }
}