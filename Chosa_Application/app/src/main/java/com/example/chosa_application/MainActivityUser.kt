package com.example.chosa_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chosa_application.databinding.ActivityMainUserBinding

class MainActivityUser : AppCompatActivity() {

    private lateinit var binding: ActivityMainUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //providing navigational function for the user
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

    }
}
