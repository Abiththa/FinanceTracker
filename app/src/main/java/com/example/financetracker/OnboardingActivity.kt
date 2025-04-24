package com.example.financetracker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class OnboardingActivity : AppCompatActivity() {

    private lateinit var onboardingViewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        onboardingViewPager = findViewById(R.id.viewPagerOnboarding)

        val items = listOf(
            OnboardingItem("Welcome to Finance Tracker", "Track your income and expenses with ease."),
            OnboardingItem("Manage Transactions", "Add, edit, and track your transactions."),
            OnboardingItem("Stay on Budget", "Stay within your budget and monitor your spending.")
        )

        val adapter = OnboardingAdapter(items) {
            // Navigate to PasscodeActivity when "Start" button is clicked
            startActivity(Intent(this, PasscodeActivity::class.java))
            finish()
        }

        onboardingViewPager.adapter = adapter
    }
}
