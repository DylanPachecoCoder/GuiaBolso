package com.fatec.guiabolsodylan.ui.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.ajchagas.guiabolsobrq.validator.ValidacaoPadrao
import com.fatec.guiabolsodylan.R
import com.fatec.guiabolsodylan.database.GuiaBolsoDatabase
import com.fatec.guiabolsodylan.database.asynctask.SalvaContaTask
import com.fatec.guiabolsodylan.database.dao.ContaDAO
import com.fatec.guiabolsodylan.database.dao.TransacaoDAO
import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.model.TipoTransacao
import com.fatec.guiabolsodylan.model.Transacao
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.toolbar.*
import java.math.BigDecimal


class CadastroContaActivity : AppCompatActivity() {

    private lateinit var contaDAO : ContaDAO

    private val validators : MutableList<ValidacaoPadrao> = mutableListOf()
    private val app_name = "Cadastro Conta"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        configuraDAO()
        configuraToolBar()
        configuraSpinner()
        validaCamposPreenchido()
        configuraBotaoSalvar()
        configuraBotaoCancelar()

    }


    private fun configuraDAO() {
        val database = GuiaBolsoDatabase.getInstance(this)
        contaDAO = database.contaDAO()
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
                finish()
            }
        }
    }

    private fun salvaConta() {
        val apelido = cadastro_edit_text_apelido.text.toString()
        val nomeTitular = cadastro_edit_text_nome_titular.text.toString()
        val agencia = cadastro_edit_text_agencia.text.toString()
        val conta = cadastro_edit_text_conta.text.toString()
        val novaConta = Conta(apelido, nomeTitular, agencia, conta, BigDecimal(00.65))
        SalvaContaTask(contaDAO, novaConta).execute()
    }

    private fun validaTodosOsCampos() : Boolean{
        var estaValido = true
        for (validator in validators){
            if(!validator.estaValido()){
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

    private fun configuraSpinner() {
        val spinnerBancos = cadastro_spinner_bancos
        val listBancos = spinnerBancos.resources.getStringArray(R.array.lista_bancos)
        spinnerBancos.adapter =
            ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                listBancos)
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
