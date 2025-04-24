package com.example.financetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OnboardingAdapter(
    private val items: List<OnboardingItem>,
    private val onStartClicked: () -> Unit
) : RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_onboarding, parent, false)
        return OnboardingViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.title
        holder.description.text = item.description

        // Show button only on last item
        if (position == items.size - 1) {
            holder.startButton.visibility = View.VISIBLE
            holder.startButton.setOnClickListener {
                onStartClicked()
            }
        } else {
            holder.startButton.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = items.size

    inner class OnboardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.titleText)
        val description: TextView = itemView.findViewById(R.id.descriptionText)
        val startButton: Button = itemView.findViewById(R.id.startButton)
    }
}
