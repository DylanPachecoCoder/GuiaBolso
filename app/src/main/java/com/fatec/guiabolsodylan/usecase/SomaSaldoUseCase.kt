package com.fatec.guiabolsodylan.usecase

import com.fatec.guiabolsodylan.model.Conta
import java.math.BigDecimal

class SomaSaldoUseCase {
    fun somaSaldo(contas: List<Conta>): BigDecimal {
        var saldo = BigDecimal.ZERO
        for (conta: Conta in contas) {
            saldo += conta.saldo
        }
        return saldo
    }
}