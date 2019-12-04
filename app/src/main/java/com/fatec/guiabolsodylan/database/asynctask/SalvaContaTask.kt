package com.fatec.guiabolsodylan.database.asynctask

import android.os.AsyncTask
import com.fatec.guiabolsodylan.database.dao.ContaDAO
import com.fatec.guiabolsodylan.model.Conta

class SalvaContaTask(private val dao: ContaDAO,
                     private val novaConta: Conta) : AsyncTask<Void, Void, Conta>(){
    override fun doInBackground(vararg params: Void?): Conta {
        dao.add(novaConta)
        return novaConta
    }

}
