package com.example.chosa_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chosa_application.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.textView4.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val email = binding.regemail.text.toString()
            val password = binding.regpassword.text.toString()
            val username = binding.username.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = firebaseAuth.currentUser?.uid

                            userId?.let { uid ->
                                // Create a "users" node in the database and store user details
                                val userReference = database.reference.child("users").child(uid)
                                val userData = mapOf(
                                    "email" to email,
                                    "username" to username
                                )

                                userReference.setValue(userData).addOnCompleteListener { dbTask ->
                                    if (dbTask.isSuccessful) {
                                        Toast.makeText(
                                            this,
                                            "User Registered Successfully.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        val intent = Intent(this, LoginActivity::class.java)
                                        startActivity(intent)
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "User Registration Failed.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(this, "User Registration Failed.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please Enter All Required Fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}