package br.com.ajchagas.guiabolsobrq.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.ajchagas.guiabolsobrq.model.listaExtratoApi.Data
import com.fatec.guiabolsodylan.model.TipoTransacao
import com.fatec.guiabolsodylan.R
import kotlinx.android.synthetic.main.extrato_item_transacao.view.*

class ListTransacoesAdapter(
    private val listaTransacoes: MutableList<Data> = mutableListOf(),
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun atualiza(transacoes: List<Data>) {
        notifyItemRangeRemoved(0, this.listaTransacoes.size)
        this.listaTransacoes.clear()
        this.listaTransacoes.addAll(transacoes)
        notifyItemRangeInserted(0, this.listaTransacoes.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.extrato_item_transacao, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaTransacoes.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val transacao = listaTransacoes[position]
        holder.bindView(transacao)
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    fun RecyclerView.ViewHolder.bindView(transacao: Data) {
        val nome = itemView.list_transacoes_nome_transacao
        val data = itemView.list_transacoes_data_transacao
        val valor= itemView.list_transacoes_valor_transacao

        nome.text = transacao.lancamento
        data.text = transacao.data_operacao
        valor.text = transacao.valor.toString()
//        if(transacao.tipo_operacao == TipoTransacao.Credito.toString()){
//            valor.text = transacao.valor
//        }else{
//            valor.text = "- " + transacao.valor
//        }


    }

}
