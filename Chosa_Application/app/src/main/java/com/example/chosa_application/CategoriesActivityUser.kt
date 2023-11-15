package com.example.chosa_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.chosa_application.databinding.ActivityCategoriesUserBinding
import com.google.firebase.database.*

class CategoriesActivityUser : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriesUserBinding
    private lateinit var adapter: CatAdapter
    private lateinit var catList: ArrayList<CatModel>

    var eventListener: ValueEventListener? = null
    var dbRef: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gridLayoutManager = GridLayoutManager(this@CategoriesActivityUser, 1)
        binding.categories.layoutManager = gridLayoutManager

        val builder = AlertDialog.Builder(this@CategoriesActivityUser)
        builder.setCancelable(false)
        builder.setView(R.layout.progress)
        val dialog = builder.create()
        dialog.show()

        catList = ArrayList()
        adapter = CatAdapter(this@CategoriesActivityUser, catList)
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

        adapter.setOnItemClickListener(object : CatAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val selectedCategory = catList[position].catName
                val intent = Intent(this@CategoriesActivityUser, OrganisationActivityUser::class.java)
                intent.putExtra("selectedCategory", selectedCategory)
                startActivity(intent)
            }
        })
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