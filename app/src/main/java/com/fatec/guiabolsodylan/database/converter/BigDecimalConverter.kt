package com.fatec.guiabolsodylan.database.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

class BigDecimalConverter {

    @TypeConverter
    fun paraString(valor : BigDecimal) : String{
        return valor.toString()
    }

    @TypeConverter
    fun paraBigDecimal(valor : String) : BigDecimal{
        return BigDecimal(valor)
    }


}