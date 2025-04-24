package com.example.financetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Transaction(
    val id: String,
    val type: String,
    val title: String,
    val amount: Double,
    val category: String,
    val date: String
)

class TransactionAdapter(
    private var transactions: MutableList<Transaction>,
    private val onUpdateClick: (Transaction) -> Unit,
    private val onDeleteClick: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    // ViewHolder class to hold the views for each item
    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val typeTextView: TextView = itemView.findViewById(R.id.tvTransactionType)
        val titleTextView: TextView = itemView.findViewById(R.id.tvTransactionTitle)
        val amountTextView: TextView = itemView.findViewById(R.id.tvTransactionAmount)
        val categoryTextView: TextView = itemView.findViewById(R.id.tvTransactionCategory)
        val dateTextView: TextView = itemView.findViewById(R.id.tvTransactionDate)
        val updateButton: Button = itemView.findViewById(R.id.btnUpdate)
        val deleteButton: Button = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_item, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.typeTextView.text = transaction.type
        holder.titleTextView.text = transaction.title
        holder.amountTextView.text = "Rs. %.2f".format(transaction.amount)
        holder.categoryTextView.text = transaction.category
        holder.dateTextView.text = transaction.date

        holder.updateButton.setOnClickListener {
            onUpdateClick(transaction)
        }
        holder.deleteButton.setOnClickListener {
            onDeleteClick(transaction)
        }
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    fun setTransactions(newTransactions: List<Transaction>) {
        transactions = newTransactions.toMutableList()
        notifyDataSetChanged()
    }
}
