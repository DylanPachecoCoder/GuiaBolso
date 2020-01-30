package com.fatec.guiabolsodylan.usecase

import androidx.lifecycle.LiveData
import com.fatec.guiabolsodylan.model.listaExtratoApi.Extrato
import com.fatec.guiabolsodylan.repository.ExtratoRepository
import com.fatec.guiabolsodylan.repository.Resource

class DetalhesContaUseCase(
    private val repository: ExtratoRepository
) {
    fun buscaExtrato(contaId: Long): LiveData<Resource<List<Extrato>?>> {
        return repository.buscaExtrato(contaId)
    }
}