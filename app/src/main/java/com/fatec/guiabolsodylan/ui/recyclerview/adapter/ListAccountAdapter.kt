package br.com.ajchagas.guiabolsobrq.ui.recyclerview.adapter

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.fatec.guiabolsodylan.extension.formataMoedaParaBrasileiro
import com.fatec.guiabolsodylan.R
import com.fatec.guiabolsodylan.database.dao.ContaDAO
import com.fatec.guiabolsodylan.model.Conta
import kotlinx.android.synthetic.main.item_conta.view.*

class ListAccountAdapter(
    private var dao: ContaDAO,
    var listaContas: MutableList<Conta> = mutableListOf(),
    private val context: Context,
    var clickListener: (Conta) -> Unit = {}

) : RecyclerView.Adapter<ListAccountAdapter.ViewHolder>() {

    fun atualiza(contas: List<Conta>) {
        notifyItemRangeRemoved(0, this.listaContas.size)
        this.listaContas.clear()
        this.listaContas.addAll(contas)
        notifyItemRangeInserted(0, this.listaContas.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewCriada = LayoutInflater.from(context)
            .inflate(R.layout.item_conta, parent, false)
        return ViewHolder(viewCriada)
    }

    override fun getItemCount(): Int {
        return listaContas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val conta = listaContas[position]

        holder.bindView(conta, clickListener)

    }

    fun getConta(position: Int): Conta {
        return listaContas[position]
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnCreateContextMenuListener {

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu?.add(0, v!!.id, adapterPosition, "Remover")
        }

        fun bindView(
            contaSelecionada: Conta,
            cliclListener: (Conta) -> Unit
        ) {

            itemView.item_conta_apelido.text = contaSelecionada.apelido
            itemView.item_conta_textview_agencia.text = contaSelecionada.agencia
            itemView.item_conta_textview_numero_conta.text = contaSelecionada.numeroConta
            itemView.item_conta_textview_saldo.text = contaSelecionada.saldo.formataMoedaParaBrasileiro()
            itemView.setOnClickListener { cliclListener(contaSelecionada) }
            itemView.setOnCreateContextMenuListener(this)
        }

    }

}

