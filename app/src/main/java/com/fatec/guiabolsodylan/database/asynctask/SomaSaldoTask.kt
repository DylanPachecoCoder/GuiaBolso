package com.fatec.guiabolsodylan.database.asynctask

import android.os.AsyncTask
import android.widget.TextView
import com.fatec.guiabolsodylan.database.dao.ContaDAO
import com.fatec.guiabolsodylan.extension.formataMoedaParaBrasileiro
import com.fatec.guiabolsodylan.model.Conta
import java.math.BigDecimal

class SomaSaldoTask(private val dao: ContaDAO,
                    private var campoSaldoTotal : TextView
) : AsyncTask<Void, Void, BigDecimal>() {

    override fun doInBackground(vararg params: Void?): BigDecimal {
        val listaContas = dao.all()
        var saldo = BigDecimal.ZERO

        for(conta : Conta in listaContas){
           saldo +=  conta.saldo
        }
        return saldo
    }

    override fun onPostExecute(saldo: BigDecimal?) {
        super.onPostExecute(saldo)
        if(saldo != null){
            campoSaldoTotal.text = saldo.formataMoedaParaBrasileiro()
        }
    }

}
