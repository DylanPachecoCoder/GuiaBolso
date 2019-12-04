package com.fatec.guiabolsodylan.database.converter

import androidx.room.TypeConverter
import java.util.*

class CalendarConverter {

    @TypeConverter
    fun paraLong(valor: Calendar?): Long? {
        return valor?.timeInMillis
    }

    @TypeConverter
    fun paraCalendar(valor: Long?): Calendar {
        val momentoAtual = Calendar.getInstance()
        if (valor != null) {
            momentoAtual.timeInMillis = valor
        }
        return momentoAtual
    }
}