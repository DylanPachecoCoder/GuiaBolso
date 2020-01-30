package com.fatec.guiabolsodylan.usecase

import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.repository.ContaRepository

class RemoveContaUseCase(
    private val repository: ContaRepository
) {
    fun removeConta(conta: Conta) {
        repository.removeConta(conta)
    }
}