package com.example.chosa_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chosa_application.databinding.ActivityDonationBinding

class DonationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDonationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDonationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.clothingButton.setOnClickListener {
            val intent = Intent(this, ClothingDActivity::class.java)
            startActivity(intent)
        }
        binding.foodButton.setOnClickListener {
            val intent = Intent(this, FoodDActivity::class.java)
            startActivity(intent)
        }
        binding.fundsButton.setOnClickListener {
            val intent = Intent(this, FundsDActivity::class.java)
            startActivity(intent)
        }

        //navigation
        binding.homeButton.setOnClickListener {
            val intent = Intent(this, MainActivityUser::class.java)
            startActivity(intent)
        }
        binding.catButton.setOnClickListener {
            val intent = Intent(this, CategoriesActivityUser::class.java)
            startActivity(intent)
        }
        binding.dashButton.setOnClickListener {
            val intent = Intent(this, DashboardActivityUser::class.java)
            startActivity(intent)
        }
        binding.backB3.setOnClickListener {
            val intent = Intent(this, OrganisationActivityUser::class.java)
            startActivity(intent)
        }
    }

}