package com.fatec.guiabolsodylan.usecase

import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.repository.ContaRepository

class EditaApelidoUseCase(
    private val repository: ContaRepository
) {

    fun editaApelido(conta: Conta) {
        repository.edita(conta)
    }
}