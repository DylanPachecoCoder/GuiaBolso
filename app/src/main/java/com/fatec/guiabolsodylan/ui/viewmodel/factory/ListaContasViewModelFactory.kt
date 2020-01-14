package com.fatec.guiabolsodylan.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fatec.guiabolsodylan.repository.Repository
import com.fatec.guiabolsodylan.ui.viewmodel.ListaContasActivityViewModel

class ListaContasViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListaContasActivityViewModel(repository) as T
    }

}
