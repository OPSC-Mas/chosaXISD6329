package com.example.chosa_application

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.chosa_application.databinding.ActivityAddOrgBinding
import com.google.firebase.database.*
import java.util.*

class AddOrgActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddOrgBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var catSpinner: Spinner
    private var selectedCategory: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddOrgBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //dropdown menu
        catSpinner = binding.spinner
        dbRef = FirebaseDatabase.getInstance().reference.child("categories")

        val categoryList: MutableList<String> = ArrayList()
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, categoryList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        catSpinner.adapter = adapter

        catSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCategory = categoryList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedCategory = null
            }
        }

        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (categorySnapshot in snapshot.children) {
                    val categoryName = categorySnapshot.key
                    categoryName?.let { categoryList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })

        binding.addorgButton.setOnClickListener {
            if (binding.addorgname.text.isNotEmpty() && binding.addorgdesc.text.isNotEmpty() && selectedCategory != null) {

                val orgName = binding.addorgname.text.toString()
                val orgDesc = binding.addorgdesc.text.toString()
                val category = selectedCategory

                dbRef = FirebaseDatabase.getInstance().getReference("organisations")
                val organisation = OrgModel(orgName, orgDesc, category)

                dbRef.child(orgName).setValue(organisation).addOnSuccessListener {
                    binding.addorgname.text.clear()
                    binding.addorgdesc.text.clear()
                    selectedCategory = null

                    Toast.makeText(this, "Organisation Created Successfully.", Toast.LENGTH_SHORT)
                        .show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Oops! Something Went Wrong.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please Enter All Required Fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
