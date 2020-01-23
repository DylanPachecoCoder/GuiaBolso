package com.fatec.guiabolsodylan.extension

import android.app.Activity
import android.widget.Toast

fun Activity.mostra(mensagem: String) {
    Toast.makeText(
        this,
        mensagem,
        Toast.LENGTH_LONG
    ).show()
}