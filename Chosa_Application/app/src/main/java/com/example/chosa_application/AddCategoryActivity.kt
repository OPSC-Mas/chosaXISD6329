package com.example.chosa_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chosa_application.databinding.ActivityAddCategoryBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddCategoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddCategoryBinding
    lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addCatButton.setOnClickListener {

            if (binding.addcatname.text.isNotEmpty() && binding.addcatdesc.text.isNotEmpty()) {
                val catName = binding.addcatname.text.toString()
                val catDesc = binding.addcatdesc.text.toString()

                dbRef = FirebaseDatabase.getInstance().getReference("categories")
                val category = CatModel(catName, catDesc)
                dbRef.child(catName).setValue(category).addOnSuccessListener {

                    binding.addcatname.text.clear()
                    binding.addcatdesc.text.clear()

                    Toast.makeText(this, "Category Created Successfully.", Toast.LENGTH_LONG).show()
                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Oops! Something Went Wrong.", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Please Enter All Required Fields.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.backB2.setOnClickListener {
            val intent = Intent(this,CategoriesMenu::class.java)
            startActivity(intent)
        }
    }
}
