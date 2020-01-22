package com.fatec.guiabolsodylan.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.widget.EditText
import com.fatec.guiabolsodylan.extension.formataParaBrasileiro
import java.util.*

class DialogExtratoActivity(private val context: Context) {

    fun configuraDataPicker(campoData: EditText) {
        val hoje = Calendar.getInstance()
        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)
        campoData.setText(hoje.formataParaBrasileiro())

        campoData.setOnClickListener {
            DatePickerDialog(context,
                { _, ano, mes, dia ->
                    val dataSelecionada = Calendar.getInstance()
                    dataSelecionada.set(ano, mes, dia)
                    campoData.setText(dataSelecionada.formataParaBrasileiro())
                }
                , ano, mes, dia)
                .show()
        }
    }

}