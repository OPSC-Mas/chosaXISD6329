package com.example.chosa_application

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.chosa_application.databinding.ActivityFoodDactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.ArrayList

class FoodDActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodDactivityBinding
    private lateinit var orgSpinner: Spinner
    private lateinit var dbRef: DatabaseReference
    private lateinit var orgAdapter: OrgAdapter
    private lateinit var orgList: ArrayList<OrgModel>
    private var selectedOrg: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodDactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the database reference for the selected organization
        orgSpinner = binding.spinner2
        dbRef = FirebaseDatabase.getInstance().getReference("organisations")

        val orgList: MutableList<String> = ArrayList()
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, orgList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        orgSpinner.adapter = adapter

        orgSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedOrg = orgList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedOrg = null
            }
        }

        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (categorySnapshot in snapshot.children) {
                    val categoryName = categorySnapshot.key
                    categoryName?.let { orgList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })

        binding.createFoodDon.setOnClickListener {
            val donContent = binding.editTextTextPersonName4.text.toString()
            val address = binding.editTextTextPersonName2.text.toString()
            val donType = "Food"
            val organisation = selectedOrg

            if (donContent.isNotEmpty()) {

                //Get user id from database
                val userId = FirebaseAuth.getInstance().currentUser?.uid
                if (userId == null) {
                    Toast.makeText(this, "Error: No User Logged In.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Create a unique key for the donation
                val donationKey = dbRef.push().key

                if (donationKey != null) {
                    // Create a DonationModel
                    dbRef = FirebaseDatabase.getInstance().getReference("users/$userId/donations")
                    val donations = DonModel(organisation, address, donContent, donType)

                    // Add the donation to the selected organization's donations
                    dbRef.child(donationKey).setValue(donations).addOnSuccessListener {
                        binding.editTextTextPersonName4.text.clear()
                        binding.editTextTextPersonName2.text.clear()
                        selectedOrg = null

                        Toast.makeText(this, "Donation Successful. A driver will be sent to your address for pick-up. More details will be sent to your email address.", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(this, "Oops! Something Went Wrong.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter all fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
