package com.fatec.guiabolsodylan.ui.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.ajchagas.guiabolsobrq.validator.ValidacaoPadrao
import br.com.alura.technews.retrofit.webclient.BancoWebClient
import com.fatec.guiabolsodylan.R
import com.fatec.guiabolsodylan.database.GuiaBolsoDatabase
import com.fatec.guiabolsodylan.database.asynctask.BaseAsyncTask
import com.fatec.guiabolsodylan.database.dao.ContaDAO
import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.model.listaBancoApi.Data
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.toolbar.colappsingtoolbar
import kotlinx.android.synthetic.main.toolbar.toolbarid2
import java.math.BigDecimal


class CadastroContaActivity : AppCompatActivity() {

    private lateinit var contaDAO: ContaDAO
    private lateinit var spinnerBancos: Spinner

    private val validators: MutableList<ValidacaoPadrao> = mutableListOf()
    private val app_name = "Cadastro Conta"
    private val webclient: BancoWebClient = BancoWebClient()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        buscaBancos()
        configuraDAO()
        configuraToolBar()
        validaCamposPreenchido()
        configuraBotaoSalvar()
        configuraBotaoCancelar()
    }

    private fun buscaBancos() {
        webclient.buscaContas(
            quandoSucesso = {
                configuraSpinner(it!!.data)
            }, quandoFalha = {
                mostra("Falha")
            })
    }

    private fun mostra(mensagem: String) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
    }


    private fun configuraDAO() {
        val database = GuiaBolsoDatabase.getInstance(this)
        contaDAO = database.contaDAO
    }

    private fun configuraBotaoCancelar() {
        cadastro_botao_cancelar.setOnClickListener {
            onBackPressed()
        }
    }

    private fun configuraBotaoSalvar() {
        cadastro_botao_Salvar.setOnClickListener { view ->

            if (validaTodosOsCampos()) {
                salvaConta()
            }
        }
    }

    private fun salvaConta() {
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
            idBanco = id)

        if(bancoSelecionado.agencia.toString() == novaConta.agencia &&
            bancoSelecionado.conta.toString() == novaConta.numeroConta){
            salvaConta(novaConta)
            finish()
        }else{
            mostra("Conta e/ou agência incorreto")
        }
    }

    private fun salvaConta(novaConta: Conta) {
        BaseAsyncTask(quandoExecuta = {
            contaDAO.add(novaConta)
        }, quandoFinaliza = {}
        ).execute()
    }

    private fun validaTodosOsCampos(): Boolean {
        var estaValido = true
        for (validator in validators) {
            if (!validator.estaValido()) {
                estaValido = false
            }
        }
        return estaValido
    }

    private fun validaCamposPreenchido() {
        validaCampoObrigatorio(cadastro_textview_apelido_layout)
        validaCampoObrigatorio(cadastro_textview_nome_layout)
        validaCampoObrigatorio(cadastro_textview_agencia_layout)
        validaCampoObrigatorio(cadastro_textview_conta_layout)
    }

    private fun validaCampoObrigatorio(textInputLayout: TextInputLayout) {
        val validacaoPadrao = ValidacaoPadrao(textInputLayout)
        val editText = textInputLayout.editText
        validators.add(validacaoPadrao)
        editText?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validacaoPadrao.estaValido()
            }
        }
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
        colappsingtoolbar.title = app_name
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
