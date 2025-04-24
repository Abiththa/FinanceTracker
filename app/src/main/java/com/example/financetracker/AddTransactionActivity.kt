package com.example.financetracker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddTransactionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        // 🔹 View IDs must match your layout XML
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupType)
        val titleInput = findViewById<EditText>(R.id.etTitle)
        val amountInput = findViewById<EditText>(R.id.etAmount)
        val categoryInput = findViewById<EditText>(R.id.etCategory)
        val dateInput = findViewById<EditText>(R.id.etDate)
        val saveButton = findViewById<Button>(R.id.btnSave)

        saveButton.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            if (selectedId == -1) {
                Toast.makeText(this, "Select transaction type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val type = findViewById<RadioButton>(selectedId).text.toString()
            val title = titleInput.text.toString()
            val amountText = amountInput.text.toString()
            val category = categoryInput.text.toString()
            val date = dateInput.text.toString()

            if (title.isEmpty() || amountText.isEmpty() || category.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if amount is a valid number
            val amount = try {
                amountText.toDouble()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Invalid amount entered", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Prepare result to send back to MainActivity
            val resultIntent = Intent()
            resultIntent.putExtra("type", type)
            resultIntent.putExtra("title", title)
            resultIntent.putExtra("amount", amount)
            resultIntent.putExtra("category", category)
            resultIntent.putExtra("date", date)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
