package com.fatec.guiabolsodylan.database.converter

import androidx.room.TypeConverter
import com.fatec.guiabolsodylan.model.TipoTransacao

class TipoTransacaoConverter {

    @TypeConverter
    fun paraString(tipo: TipoTransacao): String {
        return tipo.name
    }

    @TypeConverter
    fun paraEnum(valor: String): TipoTransacao {
        return TipoTransacao.valueOf(valor)
    }
}