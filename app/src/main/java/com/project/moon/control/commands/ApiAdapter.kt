package com.project.moon.control.commands

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.project.moon.R
import com.project.moon.entity.Commands

class ApiAdapter(private val myDataset: ArrayList<Commands>,private val myDatasetID: ArrayList<String>) :
    RecyclerView.Adapter<ApiAdapter.MyViewHolder>() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mData: DatabaseReference

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var directionCode: TextView = itemView.findViewById(R.id.tv_huongdi)
        var distance: TextView = itemView.findViewById(R.id.tv_khoangcach)
        var id: TextView = itemView.findViewById(R.id.tv_id)
        var delete: ImageView =  itemView.findViewById(R.id.delete_icon)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_api, parent, false)

        mAuth = Firebase.auth
        val user = mAuth.currentUser
        mData = Firebase.database.reference.child("User").child(user!!.uid)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.directionCode.text = myDataset[position].direction.toString()
        holder.distance.text = myDataset[position].distance.toString()
        holder.id.text = myDataset[position].createdBy
        holder.delete.setOnClickListener {
            mData =
                FirebaseDatabase.getInstance().reference.child("commands").child(myDatasetID[position])
            mData.removeValue()
        }
    }

    override fun getItemCount() = myDataset.size

}