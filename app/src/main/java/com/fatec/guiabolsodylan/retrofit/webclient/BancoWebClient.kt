package br.com.alura.technews.retrofit.webclient

import com.fatec.guiabolsodylan.model.listaExtratoApi.Extrato
import br.com.alura.technews.retrofit.AppRetrofit
import com.fatec.guiabolsodylan.model.listaBancoApi.Banco
import com.fatec.guiabolsodylan.retrofit.service.BancoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val REQUISICAO_NAO_SUCEDIDA = "Requisição não sucedida"

class BancoWebClient(
    private val service: BancoService = AppRetrofit().bancoService
) {

    private fun <T> executaRequisicao(
        call: Call<T>,
        quandoSucesso: (bancos: T?) -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    quandoSucesso(response.body())
                } else {
                    quandoFalha(REQUISICAO_NAO_SUCEDIDA)
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                quandoFalha(t.message)
            }
        })
    }

    fun buscaExtrato(
        contaId: Int,
        dataDe: String,
        dataAte: String,
        quandoSucesso: (transacoes: Extrato?) -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) {
        executaRequisicao(
            service.buscaExtrato(contaId, dataAtual = dataAte, ultimos30Dias = dataDe),
            quandoSucesso,
            quandoFalha
        )
    }

    fun buscaContas(
        quandoSucesso: (contas: Banco?) -> Unit,
        quandoFalha: (erro: String?) -> Unit
    ) {
        executaRequisicao(
            service.buscaContas(),
            quandoSucesso,
            quandoFalha
        )
    }
}
