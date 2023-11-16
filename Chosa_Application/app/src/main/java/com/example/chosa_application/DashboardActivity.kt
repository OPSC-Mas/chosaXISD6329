package com.example.chosa_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chosa_application.databinding.ActivityDashboardBinding


class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button3.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        //navigation
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