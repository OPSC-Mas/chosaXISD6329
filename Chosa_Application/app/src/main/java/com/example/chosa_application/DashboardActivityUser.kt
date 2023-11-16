package com.example.chosa_application

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.chosa_application.databinding.ActivityDashboardUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class DashboardActivityUser : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardUserBinding
    private lateinit var adapter: DonAdapter
    private lateinit var donList: ArrayList<DonModel>

    var dbRef: DatabaseReference? = null
    var eventListener: ValueEventListener? = null

    private lateinit var usernameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usernameTextView = findViewById(R.id.textView20)

        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            // The user's email, if available
            val email = user.email
            usernameTextView.text = email
        } ?: run {
            // Handle the case where there is no user logged in
            usernameTextView.text = "No user logged in"
        }

        val gridLayoutManager = GridLayoutManager(this@DashboardActivityUser, 1)
        binding.dashboard.layoutManager = gridLayoutManager

        val builder = AlertDialog.Builder(this@DashboardActivityUser)
        builder.setCancelable(false)
        builder.setView(R.layout.progress)
        val dialog = builder.create()
        dialog.show()

        //Get user id from database
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            Toast.makeText(this, "Error: No User Logged In.", Toast.LENGTH_SHORT).show()
            return
        }

        donList = ArrayList()
        adapter = DonAdapter(this@DashboardActivityUser, donList)
        binding.dashboard.adapter = adapter
        dbRef = FirebaseDatabase.getInstance().getReference("users/$userId/donations")
        dialog.show()

        eventListener = dbRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                donList.clear()
                for (itemSnapshot in snapshot.children) {
                    val donModel = itemSnapshot.getValue(DonModel::class.java)
                    if (donModel != null) {
                        donList.add(donModel)
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }
        })

        binding.button3.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
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
    }
}
