package com.fatec.guiabolsodylan.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.fatec.guiabolsodylan.ui.recyclerview.adapter.ListTransacoesAdapter
import com.fatec.guiabolsodylan.R
import com.fatec.guiabolsodylan.extension.formataMoedaParaBrasileiro
import com.fatec.guiabolsodylan.extension.formataParaBrasileiro
import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.ui.viewmodel.ExtratoActivityViewModel
import com.fatec.guiabolsodylan.ui.viewmodel.factory.ExtratoViewModelFactory
import kotlinx.android.synthetic.main.activity_extrato.*
import kotlinx.android.synthetic.main.recyclerview_list_transacoes.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class ExtratoActivity : AppCompatActivity() {

    private lateinit var conta: Conta
    private val adapter by lazy {
        ListTransacoesAdapter(context = this)
    }

    private val viewModel by lazy {
        val factory = ExtratoViewModelFactory(this)
        val provedor = ViewModelProviders.of(this, factory)
        provedor.get(ExtratoActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extrato)
//        configuraDAO()
        configuraToolBar()
        configuraDatePickerDialog()
        preencheDadosConta()
        conta = intent.getSerializableExtra("conta") as Conta
        extrato_textview_nome_titular.text = conta.nomeTitular
        extrato_textview_saldo_total.text = conta.saldo.formataMoedaParaBrasileiro()
        extrato_textview_nome_banco.text = conta.apelido
        extrato_textview_numero_agencia.text = conta.agencia
        extrato_textview_numero_conta.text = conta.numeroConta
        configuraRecyclerView()
        viewModel.buscaExtrato(contaId = conta.idBanco.toLong()).observe( this, androidx.lifecycle.Observer { transacoes->
            transacoes?.dado?.let {
                adapter.atualiza(it)
            }
        })


//        webClient.buscaExtrato(
//            conta.idBanco,
//            "20191111",
//            "20191113",
//            quandoSucesso = {
//                adapter.atualiza(it!!.data)
//            }, quandoFalha = {
//                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//            })
    }

//    private fun configuraDAO() {
//        val database = GuiaBolsoDatabase.getInstance(this)
//        transacaoDAO = database.transacaoDAO
//    }

    private fun preencheDadosConta() {
        conta = intent.getSerializableExtra("conta") as Conta
        extrato_textview_nome_titular.text = conta.nomeTitular
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

    private fun configuraRecyclerView() {
        val recyclerView = list_transacoes_recyclerview
        recyclerView.adapter = adapter
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

    private fun configuraCampoData(campoData: EditText) {
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
