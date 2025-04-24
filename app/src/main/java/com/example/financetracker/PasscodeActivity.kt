package com.example.financetracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import androidx.appcompat.app.AppCompatActivity

class PasscodeActivity : AppCompatActivity() {

    private lateinit var editPasscode: TextInputEditText
    private lateinit var btnSubmit: Button
    private val correctPasscode = "1234" // You can change it later

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passcode)

        editPasscode = findViewById(R.id.editPasscode)
        btnSubmit = findViewById(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val input = editPasscode.text.toString()
            if (input == correctPasscode) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Incorrect Passcode", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
