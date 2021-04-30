package com.project.moon.control.commands

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.project.moon.R
import com.project.moon.entity.Commands
import com.project.moon.entity.Device
import com.project.moon.entity.SocketSingleton
import com.project.moon.main.DeviceAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_commands.*
import kotlinx.android.synthetic.main.fragment_commands.view.*
import java.util.ArrayList

class CommandsFragment : Fragment() {

    private var socket = SocketSingleton.getSocket()
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mData: DatabaseReference
    private var username: String? = null
    var lstCommands: ArrayList<Commands> = ArrayList()
    var apiAdapter: Apidapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_commands, container, false)

        socket.connect()
        mAuth = Firebase.auth
        val user = mAuth.currentUser

        mData = Firebase.database.reference.child("User").child(user!!.uid)
        mData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Setting values
                username = "${dataSnapshot.child("name").value.toString()}"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(activity, "Cannot load data!", Toast.LENGTH_SHORT).show()
            }
        })

        apiAdapter = Apidapter(lstCommands)

        view.rv_api.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = apiAdapter
        }

        view.btn_send.setOnClickListener {
            val commands =
                Commands(edt_huongdi.text.toString().toInt(), edt_khoangcach.text.toString().toInt(), username!!) // instance
            lstCommands.add(commands)
            val jsonString = Gson().toJson(commands)  // json string

            socket.emit("commands", jsonString)
        }

        return view
    }
}