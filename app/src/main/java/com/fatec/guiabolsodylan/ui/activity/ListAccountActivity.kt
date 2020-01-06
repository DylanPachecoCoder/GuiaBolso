package com.fatec.guiabolsodylan.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.ajchagas.guiabolsobrq.ui.recyclerview.adapter.ListAccountAdapter
import br.com.alura.technews.retrofit.webclient.BancoWebClient
import com.fatec.guiabolsodylan.R
import com.fatec.guiabolsodylan.database.GuiaBolsoDatabase
import com.fatec.guiabolsodylan.database.asynctask.BaseAsyncTask
import com.fatec.guiabolsodylan.database.asynctask.BuscaClientesTask
import com.fatec.guiabolsodylan.database.dao.ContaDAO
import com.fatec.guiabolsodylan.extension.formataMoedaParaBrasileiro
import com.fatec.guiabolsodylan.model.Conta
import kotlinx.android.synthetic.main.activity_list_account.*
import kotlinx.android.synthetic.main.recycler_view_list_account.*
import java.math.BigDecimal

class ListAccountActivity : AppCompatActivity() {

    private val webclient: BancoWebClient = BancoWebClient()
    private lateinit var dao : ContaDAO

    private val adapter by lazy {
        ListAccountAdapter(dao, context = this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_account)
        configuraDAO()
        configuraRecyclerView()
        buscaContas()
        configuraFAB()
        somaSaldo()
//        webclient.buscaTodas(
//            quandoSucesso = { noticiasNovas ->
//                noticiasNovas?.let {
//                    Toast.makeText(this, it[0].toString(), Toast.LENGTH_SHORT).show()
//                }
//            }, quandoFalha = {
//                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//            }
//        )
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

            for(conta : Conta in listaContas){
                saldo +=  conta.saldo
            }
            saldo
        }, quandoFinaliza = { saldo ->
            item_saldo_total_valor.text = saldo.formataMoedaParaBrasileiro()
        }).execute()
    }

    private fun buscaContas() {
        BuscaClientesTask(dao, adapter).execute()
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

//    private fun listaContasParaTeste() {
//        dao.add(Conta(
//            apelido = "Itaú",
//            agencia = "1220",
//            numeroConta = "23177-5",
//            saldo = BigDecimal(1050.00)
//        ))
//        dao.add(Conta(
//            apelido = "Santander",
//            agencia = "1320",
//            numeroConta = "10000-5",
//            saldo = BigDecimal(3009.00)
//        ))
//        dao.add(Conta(
//            apelido = "Nubank",
//            agencia = "0001",
//            numeroConta = "10320-5",
//            saldo = BigDecimal(7000.23)
//        ))
//        dao.add(Conta(
//            apelido = "Itaú",
//            agencia = "1220",
//            numeroConta = "23177-5",
//            saldo = BigDecimal(1700.00)
//        ))
//        dao.add(Conta(
//            apelido = "Santander",
//            agencia = "1320",
//            numeroConta = "10000-5",
//            saldo = BigDecimal(3000.00)
//        ))
//        dao.add(Conta(
//            apelido = "Nubank",
//            agencia = "0001",
//            numeroConta = "10320-5",
//            saldo = BigDecimal(7000.00)
//        ))
//        dao.add(Conta(
//            apelido = "Itaú",
//            agencia = "1220",
//            numeroConta = "23177-5",
//            saldo = BigDecimal(1000.00)
//        ))
//        dao.add(Conta(
//            apelido = "Santander",
//            agencia = "1320",
//            numeroConta = "10000-5",
//            saldo = BigDecimal(3000.00)
//        ))
//        dao.add(Conta(
//            apelido = "Nubank",
//            agencia = "0001",
//            numeroConta = "10320-5",
//            saldo = BigDecimal(7000.00)
//        ))
//
//    }

    private fun configuraFAB() {
        fab.setOnClickListener { view ->
            abreActivityCadastroConta()
        }
    }

    private fun abreActivityCadastroConta() {
        val vaiParaActivityCadastroConta = Intent(this, CadastroContaActivity::class.java)
        startActivity(vaiParaActivityCadastroConta)
    }

}
