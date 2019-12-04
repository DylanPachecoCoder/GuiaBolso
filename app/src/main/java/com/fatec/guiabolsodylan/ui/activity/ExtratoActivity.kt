package com.fatec.guiabolsodylan.ui.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.fatec.guiabolsodylan.extension.formataParaBrasileiro
import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.model.TipoTransacao
import br.com.ajchagas.guiabolsobrq.model.Transacao
import br.com.ajchagas.guiabolsobrq.ui.recyclerview.adapter.ListTransacoesAdapter
import com.fatec.guiabolsodylan.R
import com.fatec.guiabolsodylan.extension.formataMoedaParaBrasileiro
import kotlinx.android.synthetic.main.activity_extrato.*
import kotlinx.android.synthetic.main.recyclerview_list_transacoes.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class ExtratoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extrato)
        configuraToolBar()
        configuraDatePickerDialog()
        preencheDadosConta()

        val listaTransacoes: MutableList<Transacao> = configuraListaTransacoes()
        configuraRecyclerView(listaTransacoes)
    }

    private fun preencheDadosConta() {
        val conta = intent.getSerializableExtra("conta") as Conta
        extrato_textview_saldo_total.text = conta.saldo.formataMoedaParaBrasileiro()
        extrato_textview_nome_banco.text = conta.apelido
        extrato_textview_numero_agencia.text = conta.agencia
        extrato_textview_numero_conta.text = conta.numeroConta
    }

    private fun configuraDatePickerDialog() {
        val dataDe = extrato_edittext_input_data_de
        val dataAte = extrato_edittext_input_data_ate
        configuraCampoData(dataDe)
        configuraCampoData(dataAte)
    }

    private fun configuraRecyclerView(listaClientes: MutableList<Transacao>) {
        val recyclerView = list_transacoes_recyclerview
        recyclerView.adapter = ListTransacoesAdapter(listaClientes, this)
    }

    private fun configuraListaTransacoes(): MutableList<Transacao> {
        val listaClientes: MutableList<Transacao> = mutableListOf()

        val mutableListOf = mutableListOf<Transacao>(
            Transacao("Quitanta da Marcia", "18 NOV", "RS 15,99", TipoTransacao.Credito),
            Transacao("Mercado X Loja 1", "18 NOV", "RS 15,99", TipoTransacao.Debito),
            Transacao("Sergipe", "18 NOV", "RS 15,99", TipoTransacao.Credito),
            Transacao("Alemão", "18 NOV", "RS 15,99", TipoTransacao.Debito),
            Transacao("Quitanta da Marcia", "18 NOV", "RS 15,99", TipoTransacao.Credito),
            Transacao("Mercado X Loja 1", "18 NOV", "RS 15,99", TipoTransacao.Debito),
            Transacao("Sergipe", "18 NOV", "RS 15,99", TipoTransacao.Credito),
            Transacao("Alemão", "18 NOV", "RS 15,99", TipoTransacao.Debito),
            Transacao("Quitanta da Marcia", "18 NOV", "RS 15,99", TipoTransacao.Credito),
            Transacao("Mercado X Loja 1", "18 NOV", "RS 15,99", TipoTransacao.Debito),
            Transacao("Sergipe", "18 NOV", "RS 15,99", TipoTransacao.Credito),
            Transacao("Alemão", "18 NOV", "RS 15,99", TipoTransacao.Debito),
            Transacao("Quitanta da Marcia", "18 NOV", "RS 15,99", TipoTransacao.Credito),
            Transacao("Mercado X Loja 1", "18 NOV", "RS 15,99", TipoTransacao.Debito),
            Transacao("Sergipe", "18 NOV", "RS 15,99", TipoTransacao.Credito),
            Transacao("Alemão", "18 NOV", "RS 15,99", TipoTransacao.Debito)



        )

        listaClientes.addAll(mutableListOf)
        return listaClientes
    }

    private fun configuraToolBar() {
        val toolbarid = toolbarid2
        setSupportActionBar(toolbarid)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        colappsingtoolbar.title = "Detalhes"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun configuraCampoData(campoData : EditText) {
        val hoje = Calendar.getInstance()

        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        campoData.setText(hoje.formataParaBrasileiro())
        campoData.setOnClickListener {
            DatePickerDialog(this,
                { _, ano, mes, dia ->
                    val dataSelecionada = Calendar.getInstance()
                    dataSelecionada.set(ano, mes, dia)
                    campoData.setText(dataSelecionada.formataParaBrasileiro())
                }
                , ano, mes, dia)
                .show()
        }
    }
}
