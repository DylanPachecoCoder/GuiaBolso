package com.fatec.guiabolsodylan.database.asynctask

import android.os.AsyncTask

class BaseAsyncTask<T>(
    private val quandoExecuta: () -> T,
    private val quandoFinaliza: (resultado: T) -> Unit
) : AsyncTask<Void, Void, T>() {

    override fun doInBackground(vararg params: Void?): T = quandoExecuta()

    override fun onPostExecute(result: T) {
        super.onPostExecute(result)
        quandoFinaliza(result)
    }
}