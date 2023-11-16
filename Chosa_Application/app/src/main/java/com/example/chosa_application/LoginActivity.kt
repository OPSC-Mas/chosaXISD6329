package com.example.chosa_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chosa_application.databinding.ActivityLoginActivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginActivityBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var dbRef: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance()

        binding.textView4.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
                    binding.buttonlogin.setOnClickListener{
                        val email = binding.loginemail.text.toString()
                        val password = binding.loginpassword.text.toString()
                        signIn(email, password)

                    }
            }

    // Function to check if the user is an admin
    private fun checkAdminStatus(userId: String) {
        val adminsRef = dbRef.getReference("admins")

        // Check if the user's ID exists in the "admins" table
        adminsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.hasChild(userId)) {
                    // User is an admin
                    // Redirect to the admin side of the application
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                } else {
                    // User is not an admin
                    // Redirect to the user side of the application
                    startActivity(Intent(this@LoginActivity, MainActivityUser::class.java))
                }
                finish() // Finish the login activity
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle database error
                Toast.makeText(this@LoginActivity, "Database Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Function to sign in with email and password
    fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    if (user != null) {
                        checkAdminStatus(user.uid)
                    }
                } else {
                    // Handle login error
                    Toast.makeText(this, "Login Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}