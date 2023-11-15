package com.example.chosa_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.chosa_application.databinding.ActivityOrganisationBinding
import com.google.firebase.database.*

class OrganisationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrganisationBinding
    private lateinit var adapter: OrgAdapter
    private lateinit var orgList: ArrayList<OrgModel>

    var eventListener: ValueEventListener? = null
    var dbRef: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrganisationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gridLayoutManager = GridLayoutManager(this@OrganisationActivity, 1)
        binding.organisations.layoutManager = gridLayoutManager

        val builder = AlertDialog.Builder(this@OrganisationActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress)
        val dialog = builder.create()
        dialog.show()

        orgList = ArrayList()
        adapter = OrgAdapter(this@OrganisationActivity, orgList)
        binding.organisations.adapter = adapter
        dbRef = FirebaseDatabase.getInstance().getReference("organisations")
        dialog.show()

        eventListener = dbRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                orgList.clear()
                for (itemSnapshot in snapshot.children) {
                    val orgModel = itemSnapshot.getValue(OrgModel::class.java)
                    if (orgModel != null) {
                        orgList.add(orgModel)
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