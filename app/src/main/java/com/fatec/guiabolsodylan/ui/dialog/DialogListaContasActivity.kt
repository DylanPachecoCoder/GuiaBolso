package com.fatec.guiabolsodylan.ui.dialog

import android.content.Context
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.ui.viewmodel.ListaContasActivityViewModel

class DialogListaContasActivity(
    private val context: Context,
    private val viewModel: ListaContasActivityViewModel
) {

    fun configuraContextMenuDialog(item: MenuItem, conta: Conta) {
        when (item.groupId) {
            0 -> configuraDialogEditaApelido(conta)
            1 -> criaDialogRemoverConta(conta, context)
        }
    }

    private fun configuraDialogEditaApelido(conta: Conta) {
        val (viewCriada, campoEditaApelido: TextView) = viewModel.configuraCampoEditaApelido(conta, context)
        configuraDialog(viewCriada, conta, campoEditaApelido)
    }

    private fun criaDialogRemoverConta(conta: Conta, context: Context) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Remover")
        alertDialog.setMessage("Deseja remover este cliente ?")
        alertDialog.setPositiveButton("Sim") { _, _ ->
            viewModel.removeConta(conta)
        }
        alertDialog.setNegativeButton("Não") { _, _ ->
        }
        alertDialog.show()
    }

    private fun configuraDialog(
        viewCriada: View,
        conta: Conta,
        campoEditaApelido: TextView
    ) {
        val alertDialog = AlertDialog.Builder(context)

        with(alertDialog){

            setTitle("Editar apelido")
            setView(viewCriada)
            setPositiveButton("Alterar") { _, _ ->
                conta.apelido = campoEditaApelido.text.toString()
                viewModel.editaApelido(conta)
                viewModel.buscaContas()
            }
            setNegativeButton("Cancelar") { _, _ ->
            }
            show()
        }

//        alertDialog.setTitle("Editar apelido")
//        alertDialog.setView(viewCriada)
//        alertDialog.setPositiveButton("Alterar") { _, _ ->
//            conta.apelido = campoEditaApelido.text.toString()
//            viewModel.editaApelido(conta)
//            viewModel.buscaContas()
//        }
//        alertDialog.setNegativeButton("Cancelar") { _, _ ->
//        }
//        alertDialog.show()
    }
}