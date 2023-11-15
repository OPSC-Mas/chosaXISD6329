package com.example.chosa_application

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class DonAdapter (private val context: android.content.Context, private val donList:ArrayList<DonModel>) :
    RecyclerView.Adapter<DonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.dashboard_list_item, parent, false)
        return  DonViewHolder(view)
    }

    override fun getItemCount(): Int {
        return donList.size
    }

    override fun onBindViewHolder(holder: DonViewHolder, position: Int) {
        val don = donList[position]

        // Bind your category data to the views
        holder.donType.text = don.donType
        holder.orgName.text = don.orgName
        holder.donContent.text = don.donContent

    }
}
class DonViewHolder (donView: View): RecyclerView.ViewHolder(donView){
    var donType: TextView
    var orgName: TextView
    var donContent: TextView
    var dashCard: CardView

    init{
        donType = donView.findViewById(R.id.donType)
        orgName = donView.findViewById(R.id.orgName)
        donContent = donView.findViewById(R.id.donCont)
        dashCard = donView.findViewById(R.id.dashCard)
    }
}