package com.example.chosa_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chosa_application.databinding.ActivityOrganisationUserBinding
import com.google.firebase.database.*

class OrganisationActivityUser : AppCompatActivity() {
    private lateinit var binding: ActivityOrganisationUserBinding
    private lateinit var orgRecyclerView: RecyclerView
    private lateinit var orgAdapter: OrgAdapter
    private lateinit var orgList: ArrayList<OrgModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrganisationUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        orgRecyclerView = binding.organisations
        orgList = ArrayList()
        orgAdapter = OrgAdapter(this, orgList)
        orgRecyclerView.adapter = orgAdapter
        orgRecyclerView.layoutManager = LinearLayoutManager(this)

        // Get the selected category from the previous activity or from where it's available
        val selectedCategory = intent.getStringExtra("selectedCategory")

        if (selectedCategory != null) {
            fetchOrganizations(selectedCategory)
        } else {
            // Handle the case where no category is selected, e.g., show an error message.
        }

        orgAdapter.setOnItemClickListener(object : OrgAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // Get the selected organization based on the position
                val selectedOrg = orgList[position]

                // Navigate to the DonationForOrgActivity and pass organization data if needed
                val intent = Intent(this@OrganisationActivityUser, DonationActivity::class.java)
                // Pass data using intent extras
                intent.putExtra("orgName", selectedOrg.orgName)
                intent.putExtra("orgDescription", selectedOrg.orgDesc)
                // Add more data as needed
                startActivity(intent)
            }
        })

        //providing navigational function for the users
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

    private fun fetchOrganizations(category: String) {
        val orgRef =
            FirebaseDatabase.getInstance().getReference("organisations")
        orgRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                orgList.clear()
                for (orgSnapshot in snapshot.children) {
                    val orgModel = orgSnapshot.getValue(OrgModel::class.java)
                    if (orgModel != null) {
                        orgList.add(orgModel)
                    }
                }
                orgAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }
}