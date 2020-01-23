package com.fatec.guiabolsodylan.ui.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fatec.guiabolsodylan.R
import com.fatec.guiabolsodylan.extension.mostra
import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.model.listaBancoApi.Data
import com.fatec.guiabolsodylan.ui.viewmodel.CadastroActivityViewModel
import com.fatec.guiabolsodylan.ui.viewmodel.factory.CadastroViewModelFactory
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.toolbar.*
import java.math.BigDecimal


class CadastroContaActivity : AppCompatActivity() {

    private lateinit var spinnerBancos: Spinner

    private val viewModel by lazy {
        val factory = CadastroViewModelFactory(this)
        val provedor = ViewModelProviders.of(this, factory)
        provedor.get(CadastroActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        buscaBancos()
        configuraToolBar()
        validaCamposPreenchido()
        configuraBotaoSalvar()
        configuraBotaoCancelar()
    }

    private fun buscaBancos() {
        viewModel.buscaBancos().observe(this, Observer { resources->
            resources?.dado?.let { configuraSpinner(it) }
            resources?.erro?.let { mostra("Não foi possível carregar lista de bancos") }
        })
    }

    private fun configuraBotaoCancelar() {
        cadastro_botao_cancelar.setOnClickListener {
            onBackPressed()
        }
    }

    private fun configuraBotaoSalvar() {
        cadastro_botao_Salvar.setOnClickListener { view ->
            if (viewModel.validaTodosOsCampos()) {
                salvaConta()
            }
        }
    }

    private fun validaCamposPreenchido() {
        viewModel.validaCampoObrigatorio(cadastro_textview_apelido_layout)
        viewModel.validaCampoObrigatorio(cadastro_textview_nome_layout)
        viewModel.validaCampoObrigatorio(cadastro_textview_agencia_layout)
        viewModel.validaCampoObrigatorio(cadastro_textview_conta_layout)
    }

    private fun salvaConta() {
        val (bancoSelecionado, novaConta) = pegaDadosPreenchidos()
        if(bancoSelecionado.agencia.toString() == novaConta.agencia &&
            bancoSelecionado.conta.toString() == novaConta.numeroConta){
            viewModel.salvaConta(novaConta)
            finish()
        }else{
            mostra("Conta e/ou agência incorreto")
        }
    }

    private fun pegaDadosPreenchidos(): Pair<Data, Conta> {
        val bancoSelecionado = spinnerBancos.selectedItem as Data
        val apelido = cadastro_edit_text_apelido.text.toString()
        val nomeTitular = cadastro_edit_text_nome_titular.text.toString()
        val agencia = cadastro_edit_text_agencia.text.toString()
        val conta = cadastro_edit_text_conta.text.toString()
        val id = bancoSelecionado.id

        val novaConta = Conta(
            apelido = apelido,
            nomeTitular = nomeTitular,
            agencia = agencia,
            numeroConta = conta,
            saldo = BigDecimal(00.65),
            idBanco = id
        )
        return Pair(bancoSelecionado, novaConta)
    }

    private fun configuraSpinner(listaBancos: List<Data>) {
        spinnerBancos = cadastro_spinner_bancos
        spinnerBancos.adapter =
            ArrayAdapter(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                listaBancos
            )
    }

    private fun configuraToolBar() {
        val toolbarid = toolbarid2
        setSupportActionBar(toolbarid)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        colappsingtoolbar.title = "Cadastro Conta"
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
