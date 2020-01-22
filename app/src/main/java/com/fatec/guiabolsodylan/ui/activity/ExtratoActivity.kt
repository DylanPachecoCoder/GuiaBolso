package com.fatec.guiabolsodylan.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.fatec.guiabolsodylan.ui.recyclerview.adapter.ListTransacoesAdapter
import com.fatec.guiabolsodylan.R
import com.fatec.guiabolsodylan.extension.formataMoedaParaBrasileiro
import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.ui.activity.extensions.mostraErro
import com.fatec.guiabolsodylan.ui.dialog.DialogExtratoActivity
import com.fatec.guiabolsodylan.ui.viewmodel.ExtratoActivityViewModel
import com.fatec.guiabolsodylan.ui.viewmodel.factory.ExtratoViewModelFactory
import kotlinx.android.synthetic.main.activity_extrato.*
import kotlinx.android.synthetic.main.recyclerview_list_transacoes.*
import kotlinx.android.synthetic.main.toolbar.*

class ExtratoActivity : AppCompatActivity() {

    private val adapter by lazy {
        ListTransacoesAdapter(context = this)
    }

    private val dialog by lazy {
        DialogExtratoActivity(this)
    }

    private val viewModel by lazy {
        val factory = ExtratoViewModelFactory(this)
        val provedor = ViewModelProviders.of(this, factory)
        provedor.get(ExtratoActivityViewModel::class.java)
    }

    private lateinit var conta: Conta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extrato)
        configuraToolBar()
        configuraDatePickerDialog()
        preencheDadosConta()
        configuraRecyclerView()
        buscaExtrato()
    }

    private fun buscaExtrato() {
        viewModel.buscaExtrato(contaId = conta.idBanco.toLong())
            .observe(this, androidx.lifecycle.Observer { resource ->
                resource.dado?.let { adapter.atualiza(it) }
                resource.erro?.let { mostraErro("Não foi possível carregar atualizações") }
            })
    }

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
        dialog.configuraDataPicker(dataDe)
        dialog.configuraDataPicker(dataAte)
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
}
