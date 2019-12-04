package com.fatec.guiabolsodylan.database.asynctask

import android.os.AsyncTask
import br.com.ajchagas.guiabolsobrq.ui.recyclerview.adapter.ListAccountAdapter
import com.fatec.guiabolsodylan.database.dao.ContaDAO
import com.fatec.guiabolsodylan.model.Conta

class BuscaClientesTask(
    private val dao: ContaDAO,
    private val adapter: ListAccountAdapter

) : AsyncTask<Void, Void, List<Conta>>() {

    override fun doInBackground(vararg params: Void?): List<Conta> {
        return dao.all()
    }

    override fun onPostExecute(listaContas: List<Conta>?) {
        super.onPostExecute(listaContas)
        if(listaContas != null){
            adapter.listaContas = listaContas as MutableList<Conta>
        }
    }
}
