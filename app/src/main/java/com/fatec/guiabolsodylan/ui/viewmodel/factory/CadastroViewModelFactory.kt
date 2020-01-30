package com.fatec.guiabolsodylan.ui.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fatec.guiabolsodylan.database.GuiaBolsoDatabase
import com.fatec.guiabolsodylan.repository.BancoRepository
import com.fatec.guiabolsodylan.repository.ContaRepository
import com.fatec.guiabolsodylan.ui.viewmodel.CadastroActivityViewModel

class CadastroViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val bancoRepository = BancoRepository(GuiaBolsoDatabase.getInstance(context).bancoDAO)
        val contaRepository = ContaRepository(GuiaBolsoDatabase.getInstance(context).contaDAO)
        return CadastroActivityViewModel(bancoRepository, contaRepository) as T
    }

}
