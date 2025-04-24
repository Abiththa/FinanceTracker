package com.example.financetracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment() {

    private lateinit var currencySpinner: AutoCompleteTextView
    private lateinit var btnSaveCurrency: Button
    private lateinit var btnBackup: Button
    private lateinit var btnRestore: Button
    private lateinit var switchDarkMode: SwitchMaterial

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        currencySpinner = view.findViewById(R.id.spinnerCurrency)
        btnSaveCurrency = view.findViewById(R.id.btnSaveCurrency)
        btnBackup = view.findViewById(R.id.btnBackup)
        btnRestore = view.findViewById(R.id.btnRestore)
        switchDarkMode = view.findViewById(R.id.switchDarkMode)

        setupCurrencyDropdown()
        setupDarkModeSwitch()
        setupBackupRestoreButtons()

        return view
    }

    private fun setupCurrencyDropdown() {
        val currencies = listOf("LKR", "USD", "EUR", "INR")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, currencies)
        currencySpinner.setAdapter(adapter)

        val sharedPref = requireContext().getSharedPreferences("Settings", AppCompatActivity.MODE_PRIVATE)
        val savedCurrency = sharedPref.getString("currency", "LKR")
        currencySpinner.setText(savedCurrency, false)

        btnSaveCurrency.setOnClickListener {
            val selectedCurrency = currencySpinner.text.toString()
            sharedPref.edit().putString("currency", selectedCurrency).apply()
            Toast.makeText(requireContext(), "Currency saved: $selectedCurrency", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupDarkModeSwitch() {
        val sharedPref = requireContext().getSharedPreferences("Settings", AppCompatActivity.MODE_PRIVATE)
        val isDarkMode = sharedPref.getBoolean("dark_mode", false)

        switchDarkMode.isChecked = isDarkMode

        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            sharedPref.edit().putBoolean("dark_mode", isChecked).apply()
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
    }

    private fun setupBackupRestoreButtons() {
        btnBackup.setOnClickListener {
            Toast.makeText(requireContext(), "Backup feature coming soon!", Toast.LENGTH_SHORT).show()
        }

        btnRestore.setOnClickListener {
            Toast.makeText(requireContext(), "Restore feature coming soon!", Toast.LENGTH_SHORT).show()
        }
    }
}
