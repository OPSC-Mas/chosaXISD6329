package com.example.chosa_application

import android.view.*
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


class OrgAdapter(private val context: android.content.Context, private val orgList: ArrayList<OrgModel>) :
    RecyclerView.Adapter<OrgViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrgViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.organisation_list_item, parent, false)
        return OrgViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orgList.size
    }

    override fun onBindViewHolder(holder: OrgViewHolder, position: Int) {
        val org = orgList[position]
        holder.orgName.text = org.orgName
        holder.orgDesc.text = org.orgDesc
        holder.orgCategory.text = org.category

        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(position)
        }
    }
}
class OrgViewHolder (orgView: View): RecyclerView.ViewHolder(orgView){
    var orgName: TextView
    var orgDesc: TextView
    var orgCard: CardView
    var orgCategory: TextView

    init{
        orgName = orgView.findViewById(R.id.orgName)
        orgDesc = orgView.findViewById(R.id.orgDesc)
        orgCard = orgView.findViewById(R.id.orgCard)
        orgCategory = orgView.findViewById(R.id.orgCategory)
    }
}

