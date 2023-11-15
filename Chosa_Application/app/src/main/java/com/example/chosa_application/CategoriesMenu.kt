package com.example.chosa_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chosa_application.databinding.ActivityCategoriesMenuBinding

class CategoriesMenu : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriesMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoriesMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //providing navigational function for the admin
        binding.catButton2.setOnClickListener {
            val intent = Intent(this, CategoriesActivity::class.java)
            startActivity(intent)
        }

        binding.catButton4.setOnClickListener {
            val intent = Intent(this, AddCategoryActivity::class.java)
            startActivity(intent)
        }

        binding.orgButton.setOnClickListener {
            val intent = Intent(this, OrganisationActivity::class.java)
            startActivity(intent)
        }

        binding.orgButton2.setOnClickListener {
            val intent = Intent(this, AddOrgActivity::class.java)
            startActivity(intent)
        }

        binding.homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.catButton.setOnClickListener {
            val intent = Intent(this, CategoriesMenu::class.java)
            startActivity(intent)
        }

        binding.dashButton.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }

    }
}