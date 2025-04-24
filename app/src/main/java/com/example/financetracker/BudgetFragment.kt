package com.example.financetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class BudgetFragment : Fragment() {

    private lateinit var pieChart: PieChart
    private lateinit var barChart: BarChart
    private lateinit var viewModel: TransactionViewModel
    private lateinit var budgetInput: EditText
    private lateinit var budgetAlertText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_budget, container, false)

        // Initialize views
        pieChart = view.findViewById(R.id.pieChart)
        barChart = view.findViewById(R.id.barChart)
        budgetInput = view.findViewById(R.id.editTextBudget)
        budgetAlertText = view.findViewById(R.id.budgetAlertText)

        // Set up ViewModel
        viewModel = ViewModelProvider(requireActivity())[TransactionViewModel::class.java]

        // Observe LiveData for transactions
        viewModel.transactions.observe(viewLifecycleOwner) { transactions ->
            updateCharts(transactions)
        }

        return view
    }

    private fun updateCharts(transactions: List<Transaction>) {
        // Filter by "Income" and "Expense"
        val incomeTotal = transactions.filter { it.type == "Income" }.sumOf { it.amount }
        val expenseTotal = transactions.filter { it.type == "Expense" }.sumOf { it.amount }

        // Update PieChart
        val pieEntries = listOf(
            PieEntry(incomeTotal.toFloat(), "Income"),
            PieEntry(expenseTotal.toFloat(), "Expense")
        )
        val pieDataSet = PieDataSet(pieEntries, "Budget Overview")
        pieDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        pieChart.data = PieData(pieDataSet)
        pieChart.invalidate() // Refresh the PieChart

        // Update BarChart
        val barEntries = listOf(
            BarEntry(0f, incomeTotal.toFloat()),
            BarEntry(1f, expenseTotal.toFloat())
        )
        val barDataSet = BarDataSet(barEntries, "Budget Overview")
        barDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        barChart.data = BarData(barDataSet)
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(listOf("Income", "Expense"))
        barChart.invalidate() // Refresh the BarChart

        // Check if the expenses exceed the budget
        val userBudget = budgetInput.text.toString().toDoubleOrNull() ?: 0.0
        if (userBudget > 0 && expenseTotal > userBudget) {
            budgetAlertText.text = "Warning: Expenses have exceeded your budget!"
        } else {
            budgetAlertText.text = ""
        }
    }
}
