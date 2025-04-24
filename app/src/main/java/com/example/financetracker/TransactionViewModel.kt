package com.example.financetracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransactionViewModel : ViewModel() {
    val transactions = MutableLiveData<List<Transaction>>()

    fun addTransaction(transaction: Transaction) {
        val currentList = transactions.value?.toMutableList() ?: mutableListOf()
        currentList.add(transaction)
        transactions.value = currentList
    }

    fun removeTransaction(transaction: Transaction) {
        val currentList = transactions.value?.toMutableList() ?: mutableListOf()
        currentList.remove(transaction)
        transactions.value = currentList
    }
}

