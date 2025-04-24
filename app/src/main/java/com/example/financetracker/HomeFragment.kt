package com.example.financetracker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.UUID

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var viewModel: TransactionViewModel
    private lateinit var adapter: TransactionAdapter
    private lateinit var transactionListView: RecyclerView

    private val transactionResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val type = data?.getStringExtra("transaction_type")
                val title = data?.getStringExtra("title")
                val amount = data?.getDoubleExtra("amount", 0.0)
                val category = data?.getStringExtra("category")
                val date = data?.getStringExtra("date")

                val transaction = Transaction(
                    UUID.randomUUID().toString(),
                    type ?: "",
                    title ?: "",
                    amount ?: 0.0,
                    category ?: "",
                    date ?: ""
                )
                viewModel.addTransaction(transaction)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[TransactionViewModel::class.java]

        transactionListView = view.findViewById(R.id.rvTransactions)
        adapter = TransactionAdapter(mutableListOf(), ::onUpdateClick, ::onDeleteClick)
        transactionListView.layoutManager = LinearLayoutManager(requireContext())
        transactionListView.adapter = adapter

        val savedTransactions = loadTransactionsFromPrefs()
        savedTransactions.forEach { viewModel.addTransaction(it) }

        viewModel.transactions.observe(viewLifecycleOwner) { transactions ->
            adapter.setTransactions(transactions)
            updateBalance()
            saveTransactionsToPrefs(transactions)
        }

        val addIncomeButton = view.findViewById<Button>(R.id.btnAddIncome)
        val addExpenseButton = view.findViewById<Button>(R.id.btnAddExpense)

        addIncomeButton.setOnClickListener {
            val intent = Intent(requireContext(), AddTransactionActivity::class.java)
            intent.putExtra("transaction_type", "Income")
            transactionResultLauncher.launch(intent)
        }

        addExpenseButton.setOnClickListener {
            val intent = Intent(requireContext(), AddTransactionActivity::class.java)
            intent.putExtra("transaction_type", "Expense")
            transactionResultLauncher.launch(intent)
        }
    }

    private fun onUpdateClick(transaction: Transaction) {
        val intent = Intent(requireContext(), AddTransactionActivity::class.java)
        intent.putExtra("transaction_type", transaction.type)
        intent.putExtra("title", transaction.title)
        intent.putExtra("amount", transaction.amount)
        intent.putExtra("category", transaction.category)
        intent.putExtra("date", transaction.date)
        startActivity(intent)
    }

    private fun onDeleteClick(transaction: Transaction) {
        viewModel.removeTransaction(transaction)
        Toast.makeText(requireContext(), "Transaction deleted", Toast.LENGTH_SHORT).show()
    }

    private fun saveTransactionsToPrefs(transactions: List<Transaction>) {
        val sharedPref = requireContext().getSharedPreferences("finance_data", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val gson = Gson()
        val json = gson.toJson(transactions)
        editor.putString("transaction_list", json)
        editor.apply()
    }

    private fun loadTransactionsFromPrefs(): List<Transaction> {
        val sharedPref = requireContext().getSharedPreferences("finance_data", Context.MODE_PRIVATE)
        val json = sharedPref.getString("transaction_list", null)
        return if (json != null) {
            val type = object : TypeToken<List<Transaction>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }

    private fun updateBalance() {
        val totalIncome = viewModel.transactions.value?.filter { it.type == "Income" }?.sumOf { it.amount } ?: 0.0
        val totalExpense = viewModel.transactions.value?.filter { it.type == "Expense" }?.sumOf { it.amount } ?: 0.0
        val balance = totalIncome - totalExpense

        val balanceTextView = view?.findViewById<TextView>(R.id.tvBalance)
        val incomeTextView = view?.findViewById<TextView>(R.id.tvTotalIncome)
        val expenseTextView = view?.findViewById<TextView>(R.id.tvTotalExpense)

        balanceTextView?.text = "Balance: Rs. %.2f".format(balance)
        incomeTextView?.text = "Total Income: Rs. %.2f".format(totalIncome)
        expenseTextView?.text = "Total Expense: Rs. %.2f".format(totalExpense)
    }


}
