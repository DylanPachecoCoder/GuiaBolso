package com.fatec.guiabolsodylan.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.fatec.guiabolsodylan.R
import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.ui.viewmodel.ListaContasActivityViewModel
import kotlinx.android.synthetic.main.edita_epelido.view.*

class DialogListaContasActivity(
    private val context: Context,
    private val viewModel: ListaContasActivityViewModel
) {

    fun configuraContextMenuDialog(item: MenuItem, conta: Conta) {

        when (item.groupId) {
            0 -> configuraDialog(
                conta,
                "Editar apelido",
                "Cancelar",
                "Alterar"
            )
            1 -> configuraDialog(
                conta,
                "Remover",
                "Não",
                "Sim",
                "Deseja remover esta conta ?")
        }
    }

    private fun configuraDialog(
        conta: Conta,
        titulo: String,
        botaoNegativo: String,
        botaoPositivo: String,
        mensagem: String = ""
    ) {
        val alertDialog = AlertDialog.Builder(context)
        with(alertDialog){
            setTitle(titulo)
            setNegativeButton(botaoNegativo) { _, _ -> }

            when(titulo){
                "Editar apelido"-> {
                    configuraCamposDialogEdita(conta, botaoPositivo)
                }
                "Remover" -> {
                    configuraCamposDialogRemove(conta, botaoPositivo, mensagem)
                }
            }
            show()
        }
    }

    private fun AlertDialog.Builder.configuraCamposDialogEdita(
        conta: Conta,
        botaoPositivo: String
    ) {
        val (viewCriada, campoEditaApelido: TextView) = criaViewEditaApelido(conta)
        setView(viewCriada)
        setPositiveButton(botaoPositivo) { _, _ ->
            conta.apelido = campoEditaApelido.text.toString()
            viewModel.editaApelido(conta)
        }
    }

    private fun AlertDialog.Builder.configuraCamposDialogRemove(
        conta: Conta,
        botaoPositivo: String,
        mensagem: String
    ) {
        setMessage(mensagem)
        setPositiveButton(botaoPositivo) { _, _ ->
            viewModel.removeConta(conta)
        }
    }

    private fun AlertDialog.Builder.criaViewEditaApelido(conta: Conta): Pair<View, TextView> {

        val viewCriada = LayoutInflater.from(context)
            .inflate(R.layout.edita_epelido, null, false)
        val campoEditaApelido: TextView = viewCriada.campo_edita_apelido
        campoEditaApelido.text = conta.apelido
        return Pair(viewCriada, campoEditaApelido)
    }
}