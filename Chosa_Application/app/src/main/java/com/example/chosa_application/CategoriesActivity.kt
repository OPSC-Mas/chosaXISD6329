package com.example.chosa_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.chosa_application.databinding.ActivityCategoriesBinding
import com.google.firebase.database.*

class CategoriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriesBinding
    private lateinit var adapter: CatAdapter
    private lateinit var catList: ArrayList<CatModel>

    var eventListener: ValueEventListener? = null
    var dbRef: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gridLayoutManager = GridLayoutManager(this@CategoriesActivity, 1)
        binding.categories.layoutManager = gridLayoutManager

        val builder = AlertDialog.Builder(this@CategoriesActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress)
        val dialog = builder.create()
        dialog.show()

        catList = ArrayList()
        adapter = CatAdapter(this@CategoriesActivity, catList)
        binding.categories.adapter = adapter
        dbRef = FirebaseDatabase.getInstance().getReference("categories")
        dialog.show()

        eventListener = dbRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                catList.clear()
                for (itemSnapshot in snapshot.children) {
                    val catModel = itemSnapshot.getValue(CatModel::class.java)
                    if (catModel != null) {
                        catList.add(catModel)
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }
        })


        //providing navigational function for the admin
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