package com.example.financetracker

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ReminderFragment : Fragment(R.layout.fragment_reminder) {

    private lateinit var reminderAdapter: ReminderAdapter
    private lateinit var reminderRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val reminderList = mutableListOf<Reminder>()
        reminderAdapter = ReminderAdapter(reminderList)

        reminderRecyclerView = view.findViewById(R.id.reminderRecyclerView)
        reminderRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        reminderRecyclerView.adapter = reminderAdapter

        val btnAddReminder = view.findViewById<Button>(R.id.btnAddReminder)
        btnAddReminder.setOnClickListener {
            showAddReminderDialog()
        }
    }

    private fun showAddReminderDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_reminder, null)
        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        val edtReminderTitle = dialogView.findViewById<EditText>(R.id.edtReminderTitle)
        val timePicker = dialogView.findViewById<TimePicker>(R.id.timePicker)
        val btnSaveReminder = dialogView.findViewById<Button>(R.id.btnSaveReminder)

        timePicker.setIs24HourView(true)

        btnSaveReminder.setOnClickListener {
            val title = edtReminderTitle.text.toString()
            val hour = timePicker.hour
            val minute = timePicker.minute

            val reminder = Reminder(title, hour, minute)
            reminderAdapter.addReminder(reminder)
            alertDialog.dismiss()
        }

        alertDialog.show()
    }
}
