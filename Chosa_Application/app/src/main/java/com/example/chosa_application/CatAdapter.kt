package com.example.chosa_application

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class CatAdapter (private val context: android.content.Context, private val catList:ArrayList<CatModel>) :
    RecyclerView.Adapter<CatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.category_list_item, parent, false)
        return  CatViewHolder(view)
    }

    private var listener: OnItemClickListener? = null

    // Define the interface for item click events
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    // Set the click listener for the adapter
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return catList.size
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = catList[position]

        // Bind your category data to the views
        holder.catName.text = cat.catName
        holder.catDesc.text = cat.catDesc

        // Set click listener for the item
        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }
    }

}
class CatViewHolder (catView: View): RecyclerView.ViewHolder(catView){
    var catName: TextView
    var catDesc: TextView
    var catCard: CardView

    init{
        catName = catView.findViewById(R.id.catName)
        catDesc = catView.findViewById(R.id.catDesc)
        catCard = catView.findViewById(R.id.catCard)
    }
}