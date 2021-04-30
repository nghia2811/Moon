package com.project.moon.control.commands

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.moon.R
import com.project.moon.entity.Commands

class Apidapter(private val myDataset: ArrayList<Commands>) :
    RecyclerView.Adapter<Apidapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var directionCode: TextView = itemView.findViewById(R.id.tv_huongdi)
        var distance: TextView = itemView.findViewById(R.id.tv_khoangcach)
        var id: TextView = itemView.findViewById(R.id.tv_id)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_api, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.directionCode.text = myDataset[position].DirectionCode.toString()
        holder.distance.text = myDataset[position].Distance.toString()
    }

    override fun getItemCount() = myDataset.size

}