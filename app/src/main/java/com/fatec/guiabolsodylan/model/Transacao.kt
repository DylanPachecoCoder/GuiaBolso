package br.com.ajchagas.guiabolsobrq.model

import com.fatec.guiabolsodylan.model.TipoTransacao

class Transacao(val nome : String,
                val data : String,
                val valor : String,
                val tipo : TipoTransacao
) {
}