package com.fatec.guiabolsodylan.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.ajchagas.guiabolsobrq.ui.recyclerview.adapter.ListAccountAdapter
import com.fatec.guiabolsodylan.R
import com.fatec.guiabolsodylan.extension.formataMoedaParaBrasileiro
import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.ui.activity.extensions.mostraErro
import com.fatec.guiabolsodylan.ui.dialog.DialogListaContasActivity
import com.fatec.guiabolsodylan.ui.viewmodel.ListaContasActivityViewModel
import com.fatec.guiabolsodylan.ui.viewmodel.factory.ListaContasViewModelFactory
import kotlinx.android.synthetic.main.activity_list_account.*
import kotlinx.android.synthetic.main.recycler_view_list_account.*

class ListAccountActivity : AppCompatActivity() {

    private val adapter by lazy {
        ListAccountAdapter(context = this)
    }

    private val dialog by lazy {
        DialogListaContasActivity(context = this, viewModel = viewModel)
    }

    private val viewModel by lazy {
        val factory = ListaContasViewModelFactory(this)
        val provedor = ViewModelProviders.of(this, factory)
        provedor.get(ListaContasActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_account)
        configuraRecyclerView()
        buscaContas()
        configuraFAB()
    }

    private fun buscaContas() {
        viewModel.buscaContas().observe(this, Observer { resource ->
            resource.dado?.let { listaDeContas ->
                adapter.atualiza(listaDeContas)
                preencheSaldo(listaDeContas)
            }
            resource.erro?.let { mostraErro(it) }
        })
    }

    private fun preencheSaldo(listaDeContas: List<Conta>) {
        val saldo = viewModel.somaSaldo(listaDeContas)
        item_saldo_total_valor.text = saldo.formataMoedaParaBrasileiro()
    }

    private fun configuraRecyclerView() {
        adapter.clickListener = this::abreExtratoActivity
        list_account_recyclerview.adapter = adapter
        registerForContextMenu(list_account_recyclerview)
    }

    private fun abreExtratoActivity(contaClicada: Conta) {
        val intent = Intent(this, ExtratoActivity::class.java)
        intent.putExtra("conta", contaClicada)
        startActivity(intent)
    }

    private fun configuraFAB() {
        fab.setOnClickListener {
            abreActivityCadastroConta()
        }
    }

    private fun abreActivityCadastroConta() {
        val vaiParaActivityCadastroConta = Intent(this, CadastroContaActivity::class.java)
        startActivity(vaiParaActivityCadastroConta)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val conta = viewModel.pegaContaSelecionada(item, adapter)
        dialog.configuraContextMenuDialog(item, conta)
        return super.onContextItemSelected(item)
    }
}
