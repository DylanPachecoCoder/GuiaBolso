package com.fatec.guiabolsodylan.database.asynctask

import android.os.AsyncTask
import br.com.ajchagas.guiabolsobrq.ui.recyclerview.adapter.ListAccountAdapter
import com.fatec.guiabolsodylan.database.dao.ContaDAO
import com.fatec.guiabolsodylan.model.Conta

class AtualizaContaTask(private val dao: ContaDAO,
                        private val adapter: ListAccountAdapter,
                        private val conta: Conta) : AsyncTask<Void, Void, List<Conta>>(){
    override fun doInBackground(vararg params: Void?): List<Conta> {
        dao.update(conta)
        return dao.all()
    }

    override fun onPostExecute(result: List<Conta>) {
        super.onPostExecute(result)
        adapter.atualiza(result)
    }


}
