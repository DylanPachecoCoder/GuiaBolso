package com.fatec.guiabolsodylan.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import br.com.ajchagas.guiabolsobrq.ui.recyclerview.adapter.ListAccountAdapter
import br.com.alura.technews.retrofit.AppRetrofit
import com.fatec.guiabolsodylan.R
import com.fatec.guiabolsodylan.database.GuiaBolsoDatabase
import com.fatec.guiabolsodylan.database.asynctask.BaseAsyncTask
import com.fatec.guiabolsodylan.database.dao.ContaDAO
import com.fatec.guiabolsodylan.extension.formataMoedaParaBrasileiro
import com.fatec.guiabolsodylan.model.Conta
import com.fatec.guiabolsodylan.repository.Repository
import com.fatec.guiabolsodylan.ui.viewmodel.ListaContasActivityViewModel
import kotlinx.android.synthetic.main.activity_list_account.*
import kotlinx.android.synthetic.main.edita_epelido.view.*
import kotlinx.android.synthetic.main.recycler_view_list_account.*
import java.math.BigDecimal

class ListAccountActivity : AppCompatActivity() {

    private lateinit var dao: ContaDAO

    private val adapter by lazy {
        ListAccountAdapter(context = this)
    }

    private val viewModel by lazy {
        //val repository = Repository(AppDatabase)
        val provedor = ViewModelProviders.of(this)
        provedor.get(ListaContasActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_account)
        configuraDAO()
        configuraRecyclerView()
        buscaContas()
        configuraFAB()
        somaSaldo()
    }

    override fun onResume() {
        super.onResume()
        buscaContas()
        somaSaldo()
    }

    private fun somaSaldo() {
        BaseAsyncTask(quandoExecuta = {
            val listaContas = dao.all()
            var saldo = BigDecimal.ZERO

            for (conta: Conta in listaContas) {
                saldo += conta.saldo
            }
            saldo
        }, quandoFinaliza = { saldo ->
            item_saldo_total_valor.text = saldo.formataMoedaParaBrasileiro()
        }).execute()
    }

    private fun buscaContas() {
        BaseAsyncTask(quandoExecuta = {
            dao.all()
        }, quandoFinaliza = { listaContas ->
            adapter.atualiza(listaContas)
        }).execute()
    }

    private fun configuraRecyclerView() {
        adapter.clickListener = this::abreExtratoActivity
        list_account_recyclerview.adapter = adapter
        registerForContextMenu(list_account_recyclerview)
    }

    private fun configuraDAO() {
        val database = GuiaBolsoDatabase.getInstance(this)
        dao = database.contaDAO()
    }

    private fun abreExtratoActivity(contaClicada: Conta) {
        val intent = Intent(this, ExtratoActivity::class.java)
        intent.putExtra("conta", contaClicada)
        startActivity(intent)
    }

    private fun configuraFAB() {
        fab.setOnClickListener { view ->
            abreActivityCadastroConta()
        }
    }

    private fun abreActivityCadastroConta() {
        val vaiParaActivityCadastroConta = Intent(this, CadastroContaActivity::class.java)
        startActivity(vaiParaActivityCadastroConta)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        val conta = pegaContaSelecionada(item)
        if (item.groupId == 0) {
            configuraDialogEditaApelido(conta)
        } else if (item.groupId == 1) {
            criaDialogRemoverConta(conta)
        }
        return super.onContextItemSelected(item)
    }

    private fun configuraDialogEditaApelido(conta: Conta) {
        val (viewCriada, campoEditaApelido: TextView) = configuraCampoEditaApelido(conta)
        configuraDialog(viewCriada, conta, campoEditaApelido)
    }

    private fun configuraDialog(
        viewCriada: View,
        conta: Conta,
        campoEditaApelido: TextView
    ) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Editar apelido")
        alertDialog.setView(viewCriada)
        alertDialog.setPositiveButton("Alterar") { _, _ ->
            conta.apelido = campoEditaApelido.text.toString()
            editaApelido(conta)
            buscaContas()
        }
        alertDialog.setNegativeButton("Cancelar") { _, _ ->
        }
        alertDialog.show()
    }

    private fun editaApelido(conta: Conta) {
        BaseAsyncTask(quandoExecuta = {
            dao.update(conta)
            dao.all()
        }, quandoFinaliza = { listaContas ->
            adapter.atualiza(listaContas)
        }).execute()
    }

    private fun configuraCampoEditaApelido(conta: Conta): Pair<View, TextView> {
        val viewCriada = LayoutInflater.from(this)
            .inflate(R.layout.edita_epelido, null, false)
        val campoEditaApelido: TextView = viewCriada.campo_edita_apelido
        campoEditaApelido.text = conta.apelido
        return Pair(viewCriada, campoEditaApelido)
    }

    private fun criaDialogRemoverConta(conta: Conta) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Remover")
        alertDialog.setMessage("Deseja remover este cliente ?")
        alertDialog.setPositiveButton("Sim") { _, _ ->
            removeConta(conta)
            somaSaldo()
        }
        alertDialog.setNegativeButton("Não") { _, _ ->
        }
        alertDialog.show()
    }

    private fun removeConta(conta: Conta) {
        BaseAsyncTask(quandoExecuta = {
            dao.remove(conta)
            dao.all()
        }, quandoFinaliza = { listaContas ->
            adapter.atualiza(listaContas)
        }).execute()
    }

    private fun pegaContaSelecionada(item: MenuItem): Conta {
        val position = item.order
        val conta = adapter.getConta(position)
        return conta
    }
}
