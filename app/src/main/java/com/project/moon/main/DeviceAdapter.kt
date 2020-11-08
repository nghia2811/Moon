package com.project.moon.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.moon.R
import com.project.moon.control.ControlActivity
import com.project.moon.entity.Device
import com.project.moon.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

class DeviceAdapter(val mContext: MainActivity, private val myDataset: ArrayList<Device>) :
    RecyclerView.Adapter<DeviceAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var username: TextView = itemView.findViewById(R.id.tv_device)
        var layout: RelativeLayout = itemView.findViewById(R.id.layout_device)
        var description: TextView = itemView.findViewById(R.id.tv_description)
        var image: ImageView = itemView.findViewById(R.id.iv_device)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_device, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.username.text = myDataset[position].name
        holder.description.text = myDataset[position].description
        Glide.with(mContext)
            .load(myDataset[position].image).into(holder.image)
        holder.layout.setOnClickListener {
            val intent = Intent(mContext, ControlActivity::class.java)
            intent.putExtra("device", myDataset[position].name)
            intent.putExtra("image", myDataset[position].image)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount() = myDataset.size

}