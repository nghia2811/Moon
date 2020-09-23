package com.project.moon.device

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.moon.R
import com.project.moon.control.ControlActivity
import com.project.moon.main.MainActivity

class DeviceAdapter(val mContext: DeviceFragment, private val myDataset: ArrayList<String>) :
    RecyclerView.Adapter<DeviceAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var username: TextView
        var layout: RelativeLayout

        init {
            username = itemView.findViewById(R.id.tv_device)
            layout = itemView.findViewById(R.id.layout_device)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_device, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.username.text = myDataset[position]
        holder.layout.setOnClickListener {
            var intent = Intent(mContext.context, ControlActivity::class.java)
            intent.putExtra("device", myDataset[position])
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount() = myDataset.size

}